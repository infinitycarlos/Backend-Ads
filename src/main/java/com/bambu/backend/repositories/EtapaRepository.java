package com.bambu.backend.repositories;

import com.bambu.backend.models.EtapaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EtapaRepository extends JpaRepository<EtapaModel, UUID> {

    void deleteByProjetoId(UUID projetoId);
    List<EtapaModel> findByProjetoId(UUID projetoId);
}
