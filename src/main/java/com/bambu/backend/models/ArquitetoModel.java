package com.bambu.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "arquiteto")
@Data
public class ArquitetoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    private String cpf;

    private String cadastro_cau;

    private String email;

    private String senha;
}