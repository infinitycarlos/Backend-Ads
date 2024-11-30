package com.bambu.backend.controllers;

import com.bambu.backend.models.EtapaModel;
import com.bambu.backend.models.ProjetoModel;
import com.bambu.backend.dtos.ProjetoDto;
import com.bambu.backend.repositories.EtapaRepository;
import com.bambu.backend.repositories.ProjetoRepository;
import com.bambu.backend.services.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping("/projetos")
    public ResponseEntity<Object> criarProjeto(@RequestBody @Valid ProjetoDto projetoDto) {
        try {
            ProjetoModel projetoSalvo = projetoService.criarProjeto(projetoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(projetoSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/projetos")
    public ResponseEntity<List<ProjetoModel>> getAllProjects(){
        return ResponseEntity.status(HttpStatus.OK).body(projetoService.getAllProjects());
    }


    @GetMapping("/projetos/{id}")
    public ResponseEntity<Object> getProjetoById(@PathVariable UUID id){
        Optional<ProjetoModel> projetoEncontrado = projetoService.findById(id);

        if (projetoEncontrado.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(projetoEncontrado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado!");
        }
    }

    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<String> deleteProjetoById(@PathVariable UUID id){
        try {
            projetoService.deleteProjetoAndAmbientes(id);
            return ResponseEntity.status(HttpStatus.OK).body("Projeto removido com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/projetos/{id}")
    public ResponseEntity<Object> updateProjetoById(@PathVariable UUID id, @RequestBody ProjetoDto projetoDto){
        try {
            ProjetoModel projetoAtualizado = projetoService.updateProjeto(id, projetoDto);
            return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
