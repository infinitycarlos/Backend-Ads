package com.bambu.backend.controllers;

import com.bambu.backend.dtos.EtapaDto;
import com.bambu.backend.models.EtapaModel;
import com.bambu.backend.repositories.EtapaRepository;
import com.bambu.backend.services.EtapaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/etapas")
public class EtapaController {

    @Autowired
    private EtapaRepository etapaRepository;

    @Autowired
    private EtapaService etapaService;

    @GetMapping("/{projetoId}")
    public ResponseEntity<List<EtapaModel>> getEtapasByProjetoId(@PathVariable UUID projetoId) {
        List<EtapaModel> etapas = etapaService.findByProjetoId(projetoId);
        if (etapas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(etapas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarEtapa(@PathVariable UUID id, @RequestBody EtapaDto etapaDto) {
        try {
            EtapaModel etapaAtualizada = etapaService.atualizarEtapa(id, etapaDto);
            return ResponseEntity.ok(etapaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
