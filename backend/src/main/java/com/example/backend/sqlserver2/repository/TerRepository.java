package com.example.backend.sqlserver2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.backend.sqlserver2.model.Ter;
import com.example.backend.sqlserver2.model.TerId;

@Repository
public interface TerRepository extends JpaRepository<Ter, TerId>, JpaSpecificationExecutor<Ter> {

}