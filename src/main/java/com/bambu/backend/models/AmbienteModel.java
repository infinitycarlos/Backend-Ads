package com.bambu.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "ambiente")
@Data
public class AmbienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    private float tamanho;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private ProjetoModel projeto;
}
