package com.bambu.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "etapa")
@Data
public class EtapaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nomeDaEtapa;

    private String descricao;

    private Date dataDeInicio;

    private Date dataPrevistaFim;

    private float valorEtapa;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    @JsonBackReference
    private ProjetoModel projeto;

    @OneToMany(mappedBy = "etapa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ReuniaoModel> reunioes;

}
