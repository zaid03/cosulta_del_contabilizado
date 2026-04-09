package com.example.backend.config;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String SECRET = "DK6x5z6H6Ms04HZZ7SgOnzchhcTudBI5kcCeeOGF7xk=";
    private static final long EXP_MIN = 1440; 
    private static final String USERNAME = "testuser";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET, EXP_MIN);
    }

    @Test
    void constructor_initializes_with_secret_and_expiration() {
        assertNotNull(jwtUtil, "JwtUtil should be initialized");
    }

    @Test
    void constructor_accepts_valid_secret() {
        JwtUtil util = new JwtUtil(SECRET, 60);
        assertNotNull(util, "JwtUtil should accept valid secret");
    }

    @Test
    void constructor_accepts_various_expiration_minutes() {
        JwtUtil exp1h = new JwtUtil(SECRET, 60);
        JwtUtil exp24h = new JwtUtil(SECRET, 1440);
        JwtUtil exp1w = new JwtUtil(SECRET, 10080);
        assertAll(
            () -> assertNotNull(exp1h),
            () -> assertNotNull(exp24h),
            () -> assertNotNull(exp1w)
        );
    }

    @Test
    void generate_creates_valid_token() {
        String token = jwtUtil.generate(USERNAME);
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Test
    void generate_creates_token_with_three_parts() {
        String token = jwtUtil.generate(USERNAME);
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length, "JWT should have 3 parts (header.payload.signature)");
    }

    @Test
    void generate_works_with_different_usernames() {
        String token1 = jwtUtil.generate("user1");
        String token2 = jwtUtil.generate("user2");
        assertAll(
            () -> assertNotNull(token1),
            () -> assertNotNull(token2),
            () -> assertNotEquals(token1, token2)
        );
    }

    @Test
    void generate_handles_special_characters_in_username() {
        String[] specialUsernames = {
            "user@example.com",
            "user.name",
            "user-name",
            "user_name",
            "用户",
            "пользователь"
        };
        
        for (String username : specialUsernames) {
            String token = jwtUtil.generate(username);
            assertNotNull(token, "Token should be generated for username: " + username);
            String retrieved = jwtUtil.validateAndGetSubject(token);
            assertEquals(username, retrieved, "Username should match for: " + username);
        }
    }

    @Test
    void validateAndGetSubject_extracts_username_from_valid_token() {
        String token = jwtUtil.generate(USERNAME);
        String subject = jwtUtil.validateAndGetSubject(token);
        assertEquals(USERNAME, subject, "Should extract correct username from token");
    }

    @Test
    void validateAndGetSubject_returns_correct_subject() {
        String[] usernames = {"alice", "bob", "charlie", "diana"};
        for (String username : usernames) {
            String token = jwtUtil.generate(username);
            String subject = jwtUtil.validateAndGetSubject(token);
            assertEquals(username, subject, "Should extract correct subject: " + username);
        }
    }

    @Test
    void validateAndGetSubject_returns_null_for_null_token() {
        assertThrows(IllegalArgumentException.class, () -> {
            jwtUtil.validateAndGetSubject(null);
        }, "Should throw IllegalArgumentException for null token");
    }

    @Test
    void validateAndGetSubject_returns_null_for_empty_token() {
        assertThrows(IllegalArgumentException.class, () -> {
            jwtUtil.validateAndGetSubject("");
        }, "Should throw IllegalArgumentException for empty token");
    }

    @Test
    void validateAndGetSubject_returns_null_for_invalid_token_format() {
        String[] invalidTokens = {
            "invalid",
            "invalid.token",
            "part1.part2.part3.part4",
            "not.valid.jwt"
        };
        
        for (String token : invalidTokens) {
            String subject = jwtUtil.validateAndGetSubject(token);
            assertNull(subject, "Should return null for invalid token: " + token);
        }
    }

    @Test
    void validateAndGetSubject_returns_null_for_tampered_signature() {
        String token = jwtUtil.generate(USERNAME);
        String[] parts = token.split("\\.");
        String tamperedToken = parts[0] + "." + parts[1] + ".tamperedsignature";
        
        String subject = jwtUtil.validateAndGetSubject(tamperedToken);
        assertNull(subject, "Should return null for tampered token");
    }

    @Test
    void validateAndGetSubject_returns_null_for_expired_token() throws InterruptedException {
        JwtUtil shortExpJwtUtil = new JwtUtil(SECRET, 0);
        String token = shortExpJwtUtil.generate(USERNAME);
        
        Thread.sleep(100); 
        
        String subject = shortExpJwtUtil.validateAndGetSubject(token);
        assertNull(subject, "Should return null for expired token");
    }

    @Test
    void validateAndGetSubject_returns_null_for_token_with_wrong_signature() {
        String token = jwtUtil.generate(USERNAME);
        
        // Create token with different secret (must be 256+ bits)
        String wrongSecret = "DifferentSecretKeyFor256BitsPlusCompliance12345679";
        JwtUtil wrongJwtUtil = new JwtUtil(wrongSecret, EXP_MIN);
        
        String subject = wrongJwtUtil.validateAndGetSubject(token);
        assertNull(subject, "Should return null when validating with wrong secret");
    }

    @Test
    void generated_token_contains_subject() {
        String token = jwtUtil.generate(USERNAME);
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        String subject = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        
        assertEquals(USERNAME, subject, "Token should contain correct subject");
    }

    @Test
    void generated_token_contains_issuedAt_claim() {
        String token = jwtUtil.generate(USERNAME);
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        Date issuedAt = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getIssuedAt();
        
        assertNotNull(issuedAt, "Token should contain issuedAt claim");
        long timeDiff = Math.abs(System.currentTimeMillis() - issuedAt.getTime());
        assertTrue(timeDiff < 5000, "issuedAt should be recent (within 5 seconds)");
    }

    @Test
    void generated_token_contains_expiration_claim() {
        String token = jwtUtil.generate(USERNAME);
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        Date expiration = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
        
        assertNotNull(expiration, "Token should contain expiration claim");
        assertTrue(expiration.getTime() > System.currentTimeMillis(), 
            "Expiration should be in the future");
    }

    @Test
    void generated_token_expiration_respects_configured_minutes() {
        long expMinutes = 120; 
        JwtUtil testJwtUtil = new JwtUtil(SECRET, expMinutes);
        
        String token = testJwtUtil.generate(USERNAME);
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        Date expiration = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
        
        long now = System.currentTimeMillis();
        long expectedExpiry = now + (expMinutes * 60_000);
        long diffMillis = Math.abs(expiration.getTime() - expectedExpiry);
        
        assertTrue(diffMillis < 2000,
            "Expiration time should be approximately " + expMinutes + " minutes from now");
    }

    @Test
    void token_roundtrip_preserves_username() {
        String original = "testuser123";
        String token = jwtUtil.generate(original);
        String retrieved = jwtUtil.validateAndGetSubject(token);
        assertEquals(original, retrieved, "Username should be preserved in round-trip");
    }

    @Test
    void multiple_tokens_for_same_user_validate_independently() {
        String token1 = jwtUtil.generate(USERNAME);
        String token2 = jwtUtil.generate(USERNAME);
        
        String subject1 = jwtUtil.validateAndGetSubject(token1);
        String subject2 = jwtUtil.validateAndGetSubject(token2);
        
        assertEquals(USERNAME, subject1);
        assertEquals(USERNAME, subject2);
    }

    @Test
    void generate_handles_empty_string_username() {
        String token = jwtUtil.generate("");
        String subject = jwtUtil.validateAndGetSubject(token);
        assertNull(subject, "Empty string username should result in null subject");
    }

    @Test
    void generate_handles_very_long_username() {
        String longUsername = "a".repeat(1000);
        String token = jwtUtil.generate(longUsername);
        String subject = jwtUtil.validateAndGetSubject(token);
        assertEquals(longUsername, subject, "Should handle very long usernames");
    }

    @Test
    void validateAndGetSubject_handles_whitespace_by_validating_normally() {
        String token = jwtUtil.generate(USERNAME);
        // JJWT trims whitespace, so token with trailing space still validates
        String subject = jwtUtil.validateAndGetSubject(token + " ");
        assertEquals(USERNAME, subject, "Should extract subject even with trailing whitespace");
    }
}
