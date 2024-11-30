package com.bambu.backend.controllers;

import com.bambu.backend.dtos.ReuniaoDto;
import com.bambu.backend.models.EtapaModel;
import com.bambu.backend.models.ReuniaoModel;
import com.bambu.backend.repositories.EtapaRepository;
import com.bambu.backend.repositories.ProjetoRepository;
import com.bambu.backend.repositories.ReuniaoRepository;
import com.bambu.backend.services.ReuniaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ReuniaoController {

    @Autowired
    private ReuniaoService reuniaoService;

    @PostMapping("/reunioes")
    public ResponseEntity<Object> createReuniao(@RequestBody ReuniaoDto reuniaoDto,
                                                @RequestParam UUID etapaId) {
        if (reuniaoDto.nomeReuniao() == null || reuniaoDto.nomeReuniao().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'nomeReuniao' é obrigatório");
        }

        if (reuniaoDto.dataReuniao() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'dataReuniao' é obrigatório");
        }

        Optional<ReuniaoModel> reuniao = reuniaoService.createReuniao(reuniaoDto, etapaId);

        if (reuniao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A etapa especificada não foi encontrada");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reuniao.get());
    }


    @GetMapping("/reunioes")
    public ResponseEntity<List<ReuniaoModel>> getAllReunioes() {
        return ResponseEntity.ok(reuniaoService.getAllReunioes());
    }

    @GetMapping("/reunioes/{etapaId}")
    public ResponseEntity<List<ReuniaoModel>> getReunioesByEtapaId(@PathVariable UUID etapaId) {
        List<ReuniaoModel> reunioes = reuniaoService.getReunioesByEtapaId(etapaId);
        if (reunioes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reunioes);
        }
        return ResponseEntity.ok(reunioes);
    }


    @DeleteMapping("/reunioes/{id}")
    public ResponseEntity<String> deleteReuniaoById(@PathVariable UUID id) {
        if (reuniaoService.deleteReuniaoById(id)) {
            return ResponseEntity.ok("Reunião removida com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reunião não encontrada");
    }

    @PutMapping("/reunioes/{id}")
    public ResponseEntity<Object> atualizarReuniao(@PathVariable UUID id, @RequestBody ReuniaoDto reuniaoDto) {
        Optional<ReuniaoModel> reuniao = reuniaoService.updateReuniao(id, reuniaoDto);
        if (reuniao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reunião não encontrada");
        }
        return ResponseEntity.ok(reuniao.get());
    }


}
