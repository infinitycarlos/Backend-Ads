package com.bambu.backend.repositories;

import com.bambu.backend.models.ArquitetoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArquitetoRepository extends JpaRepository<ArquitetoModel, UUID> {

    List<ArquitetoModel> findByNomeContainingIgnoreCase(String nome);
}
