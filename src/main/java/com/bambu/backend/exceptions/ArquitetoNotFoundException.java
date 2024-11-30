package com.bambu.backend.exceptions;

import java.util.UUID;

public class ArquitetoNotFoundException extends RuntimeException {

    public ArquitetoNotFoundException(UUID id) {
        super("Arquiteto com ID " + id + " não encontrado!");
    }
}
