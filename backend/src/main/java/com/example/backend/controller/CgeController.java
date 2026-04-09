package com.example.backend.controller;

import com.example.backend.dto.CentroGestorLogin;
import com.example.backend.sqlserver2.model.Cge;
import com.example.backend.sqlserver2.model.Dep;
import com.example.backend.sqlserver2.model.Dpe;
import com.example.backend.sqlserver2.repository.CgeRepository;
import com.example.backend.sqlserver2.repository.DepRepository;
import com.example.backend.sqlserver2.repository.DpeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cge")
public class CgeController {
    @Autowired
    private CgeRepository cgeRepository;
    @Autowired
    private DepRepository depRepository;
    @Autowired
    private DpeRepository dpeRepository;

    private static final String SIN_RESULTADO = "Sin resultado";
    private static final String ERROR = "Error :";

    //selecting centro gestor for login
    @GetMapping("/{ent}/{eje}/{percod}")
    public ResponseEntity<?> getCentrosGestores(
        @PathVariable Integer ent,
        @PathVariable String eje,
        @PathVariable String percod
    ) {
        try {
            List<Dpe> dpes = dpeRepository.findByENTAndEJEAndPERCOD(ent, eje, percod);
            if (dpes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SIN_RESULTADO);
            }

            List<String> depcods = dpes.stream()
                .map(Dpe::getDEPCOD)
                .distinct()
                .toList();

            List<Dep> deps = depRepository.findByENTAndEJEAndDEPCODIn(ent, eje, depcods);
            if (deps.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SIN_RESULTADO);
            }

            Map<String, Dep> depByCgecod = deps.stream().collect(Collectors.toMap(
                Dep::getCGECOD,
                dep -> dep,
                this::mergeDeps 
            ));

            List<String> cgecods = List.copyOf(depByCgecod.keySet());
            List<Cge> cges = cgeRepository.findByENTAndEJEAndCGECODIn(ent, eje, cgecods);
            if (cges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SIN_RESULTADO);
            }

            List<CentroGestorLogin> result = cges.stream().map(cge -> {
                Dep dep = depByCgecod.get(cge.getCGECOD());
                return new CentroGestorLogin(
                    cge,
                    dep.getDEPINT(),
                    dep.getDEPALM(),
                    dep.getDEPCOM()
                );
            }).toList();

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ERROR + ex.getMessage());
        }
    }

    private Dep mergeDeps(Dep existing, Dep replacement) {
        existing.setDEPINT(Math.max(
            existing.getDEPINT() != null ? existing.getDEPINT() : 0, 
            replacement.getDEPINT() != null ? replacement.getDEPINT() : 0
        ));
        existing.setDEPALM(Math.max(
            existing.getDEPALM() != null ? existing.getDEPALM() : 0, 
            replacement.getDEPALM() != null ? replacement.getDEPALM() : 0
        ));
        existing.setDEPCOM(Math.max(
            existing.getDEPCOM() != null ? existing.getDEPCOM() : 0, 
            replacement.getDEPCOM() != null ? replacement.getDEPCOM() : 0
        ));
        return existing;
    }
}