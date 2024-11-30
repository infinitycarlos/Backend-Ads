package com.bambu.backend.repositories;

import com.bambu.backend.models.ReuniaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;
import java.util.List;

@Repository
public interface ReuniaoRepository extends JpaRepository<ReuniaoModel, UUID> {
    List<ReuniaoModel> findAll();
    
    List<ReuniaoModel> findByNomeReuniao(String nomeReuniao);

    List<ReuniaoModel> findByDataReuniao(Date dataReuniao);

    List<ReuniaoModel> findByEtapaId(UUID etapaId);
}
