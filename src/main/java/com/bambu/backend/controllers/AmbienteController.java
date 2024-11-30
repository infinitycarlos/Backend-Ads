package com.bambu.backend.controllers;

import com.bambu.backend.dtos.AmbienteDto;
import com.bambu.backend.models.ProjetoModel;
import com.bambu.backend.services.AmbienteService;
import com.bambu.backend.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    private static final Logger logger = LoggerFactory.getLogger(AmbienteController.class);

    @Autowired
    private AmbienteService ambienteService;

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<String> criarAmbientes(@RequestParam UUID projetoId, @RequestBody List<AmbienteDto> ambientes) {

        logger.info("Recebida solicitação para criar ambientes. Projeto ID: {}", projetoId);

        Optional<ProjetoModel> projetoOptional = projetoService.findById(projetoId);
        if (projetoOptional.isEmpty()) {
            logger.warn("Projeto não encontrado. Projeto ID: {}", projetoId);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
        }

        ProjetoModel projeto = projetoOptional.get();
        ambienteService.salvarAmbientes(ambientes, projeto);

        logger.info("Ambientes criados com sucesso para o Projeto ID: {}", projetoId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Ambientes criados com sucesso.");
    }

    @GetMapping("/by-projeto")
    public ResponseEntity<List<AmbienteDto>> getAmbientesByProjeto(@RequestParam UUID projetoId) {

        logger.info("Recebida solicitação para listar ambientes. Projeto ID: {}", projetoId);

        Optional<ProjetoModel> projetoOptional = projetoService.findById(projetoId);
        if (projetoOptional.isEmpty()) {
            logger.warn("Projeto não encontrado ao listar ambientes. Projeto ID: {}", projetoId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<AmbienteDto> ambientes = ambienteService.findAmbientesByProjetoId(projetoId);

        logger.info("Listagem de ambientes concluída. Total de ambientes: {}", ambientes.size());
        return ResponseEntity.ok(ambientes);
    }

}
