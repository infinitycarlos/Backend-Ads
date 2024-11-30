package com.bambu.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArquitetoDto (@NotBlank String nome,
                          @NotBlank String cpf,
                          @NotNull String cadastro_cau,
                          @NotNull String email,
                          @NotNull String senha
){}
