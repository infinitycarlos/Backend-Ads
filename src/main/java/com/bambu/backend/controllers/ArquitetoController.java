package com.bambu.backend.controllers;

import com.bambu.backend.dtos.ArquitetoDto;
import com.bambu.backend.models.ArquitetoModel;
import com.bambu.backend.repositories.ArquitetoRepository;
import com.bambu.backend.services.ArquitetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/arquitetos")
@Tag(name = "Arquitetos", description = "Endpoints para gerenciamento de arquitetos")
public class ArquitetoController {

    private static final Logger logger = LoggerFactory.getLogger(ArquitetoController.class);

    @Autowired
    private ArquitetoService arquitetoService;

    @PostMapping
    @Operation(summary = "Criar um novo arquiteto", description = "Adiciona um novo arquiteto ao banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Arquiteto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<ArquitetoModel> criarArquiteto(@RequestBody ArquitetoDto arquitetoDto) {

        logger.info("Recebida solicitação para criar arquiteto: {}", arquitetoDto);

        ArquitetoModel arquitetoSalvo = arquitetoService.createArquiteto(arquitetoDto);

        logger.info("Arquiteto criado com sucesso. ID: {}", arquitetoSalvo.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(arquitetoSalvo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter um arquiteto pelo ID", description = "Busca os detalhes de um arquiteto pelo ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquiteto encontrado"),
            @ApiResponse(responseCode = "404", description = "Arquiteto não encontrado")
    })
    public ResponseEntity<Object> getArquitetoById(@PathVariable UUID id) {

        logger.info("Recebida solicitação para buscar arquiteto com ID: {}", id);

        Optional<ArquitetoModel> arquitetoModelOptional = arquitetoService.getArquitetoById(id);

        if (arquitetoModelOptional.isEmpty()) {
            logger.warn("Arquiteto não encontrado. ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquiteto not found!");
        }

        logger.info("Arquiteto encontrado. ID: {}", id);

        return ResponseEntity.ok(arquitetoModelOptional.get());
    }

    @GetMapping
    @Operation(summary = "Listar todos os arquitetos", description = "Retorna uma lista de todos os arquitetos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de arquitetos retornada com sucesso")
    })
    public ResponseEntity<List<ArquitetoModel>> getAllArquitetos(){
        logger.info("Recebida solicitação para listar todos os arquitetos.");

        List<ArquitetoModel> allArquitetos = arquitetoService.getAllArquitetos();
        logger.info("Listagem concluída. Total de arquitetos encontrados: {}", allArquitetos.size());

        return ResponseEntity.ok(allArquitetos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ArquitetoModel>> getArquitetosByNome(@RequestParam String nome) {
        List<ArquitetoModel> arquitetos = arquitetoService.getArquitetosByNome(nome);
        if (arquitetos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(arquitetos);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um arquiteto pelo ID", description = "Remove um arquiteto com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquiteto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Arquiteto não encontrado")
    })
    public ResponseEntity<String> deleteArquitetoById(@PathVariable UUID id){
        logger.info("Recebida solicitação para deletar arquiteto com ID: {}", id);

        if (arquitetoService.deleteArquitetoById(id)) {
            logger.info("Arquiteto deletado com sucesso. ID: {}", id);
            return ResponseEntity.ok("Arquiteto deleted successfully!");
        }
        logger.warn("Arquiteto não encontrado para exclusão. ID: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquiteto not found!");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um arquiteto", description = "Atualiza as informações de um arquiteto pelo ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquiteto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Arquiteto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Object> updateArquitetoById(@RequestBody ArquitetoDto arquitetoDto,
                                                    @PathVariable UUID id){

        logger.info("Recebida solicitação para atualizar arquiteto com ID: {}", id);

        Optional<ArquitetoModel> updatedArquiteto = arquitetoService.updateArquiteto(id, arquitetoDto);
        if (updatedArquiteto.isEmpty()) {
            logger.warn("Arquiteto não encontrado para atualização. ID: {}", id);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquiteto not found!");
        }
        logger.info("Arquiteto atualizado com sucesso. ID: {}", id);

        return ResponseEntity.ok(updatedArquiteto.get());
    }


}
