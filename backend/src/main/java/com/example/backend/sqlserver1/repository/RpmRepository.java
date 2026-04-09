package com.example.backend.sqlserver1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.sqlserver1.model.Rpm;
import com.example.backend.dto.MenuDto;

@Repository
public interface RpmRepository extends JpaRepository<Rpm, Long> {

    @Query("SELECT r.MNUCOD FROM Rpm r WHERE r.PERCOD = :PERCOD AND r.APLCOD = 7")
    List<String> findMNUCODsByPERCOD(@Param("PERCOD") String PERCOD);

    List<MenuDto> findByPERCODAndAPLCOD(String percod, Integer aplcod);
}
