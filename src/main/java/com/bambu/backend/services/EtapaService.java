package com.bambu.backend.services;

import com.bambu.backend.dtos.EtapaDto;
import com.bambu.backend.models.EtapaModel;
import com.bambu.backend.repositories.EtapaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EtapaService {

    @Autowired
    private EtapaRepository etapaRepository;

    public List<EtapaModel> findByProjetoId(UUID projetoId) {
        return etapaRepository.findByProjetoId(projetoId);
    }

    public Optional<EtapaModel> findById(UUID id) {
        return etapaRepository.findById(id);
    }

    public EtapaModel atualizarEtapa(UUID id, EtapaDto etapaDto) {
        Optional<EtapaModel> etapaOptional = etapaRepository.findById(id);
        if (etapaOptional.isEmpty()) {
            throw new IllegalArgumentException("Etapa não encontrada com o ID especificado.");
        }

        EtapaModel etapa = etapaOptional.get();
        BeanUtils.copyProperties(etapaDto, etapa, "id", "projeto", "reunioes");

        return etapaRepository.save(etapa);
    }
}
