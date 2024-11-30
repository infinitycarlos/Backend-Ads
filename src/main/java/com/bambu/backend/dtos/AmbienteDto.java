package com.bambu.backend.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;
public record AmbienteDto(@NotNull String nome,
                          @NotNull float tamanho) {
}
