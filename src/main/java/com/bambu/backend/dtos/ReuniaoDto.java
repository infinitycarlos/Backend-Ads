package com.bambu.backend.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public record ReuniaoDto(@NotNull UUID id,
                         @NotNull String nomeReuniao,
                         @NotNull Date dataReuniao,
                         String local,
                         @NotNull String apontamentos) {
}
