package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/version")
public class VersionController {
    @GetMapping("/num")
    public Map<String, String> getVersion() {
       String version = getClass().getPackage().getImplementationVersion();
    if (version != null && version.contains("-")) {
        version = version.substring(0, version.indexOf('-'));
    }
        return Map.of("version", version != null ? version : "desconocida");
    }
}
