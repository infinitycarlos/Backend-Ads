package com.bambu.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.Date;

@Entity
@Table(name = "projeto")
@Data
public class ProjetoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nomeDoProjeto;

    private String endereco;

    private String descricao;

    private Date dataDeInicio;

    private Date dataPrevistaFim;

    private String tipoObra;

    private float valorProjeto;

    private float porcentagemEtapa1;

    private float porcentagemEtapa2;

    private float porcentagemEtapa3;

    private float porcentagemEtapa4;

    private float porcentagemEtapa5;

    private String statusProjeto;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EtapaModel> etapas;

}
