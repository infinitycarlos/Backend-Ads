package com.bambu.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ProjetoDto (@NotBlank String nomeDoProjeto,
                          @NotBlank String descricao,
                          @NotNull String endereco,
                          @NotNull float valorProjeto,
                          @NotNull Date dataDeInicio,
                          @NotNull Date dataPrevistaFim,
                          @NotNull String tipoObra,
                          @NotNull float porcentagemEtapa1,
                          @NotNull float porcentagemEtapa2,
                          @NotNull float porcentagemEtapa3,
                          @NotNull float porcentagemEtapa4,
                          @NotNull float porcentagemEtapa5,
                          @NotBlank String statusProjeto
                          ){}
