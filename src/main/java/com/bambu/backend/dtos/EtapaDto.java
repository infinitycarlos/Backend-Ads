package com.bambu.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record EtapaDto(
        @NotBlank String nomeDaEtapa,
        @NotBlank String descricao,
        @NotNull Date dataDeInicio,
        @NotNull Date dataPrevistaFim,
        @NotNull float valorEtapa
) {}
