package com.bambu.backend.services;

import com.bambu.backend.dtos.ReuniaoDto;
import com.bambu.backend.models.EtapaModel;
import com.bambu.backend.models.ReuniaoModel;
import com.bambu.backend.repositories.EtapaRepository;
import com.bambu.backend.repositories.ReuniaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReuniaoService {

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    private EtapaRepository etapaRepository;

    public Optional<ReuniaoModel> createReuniao(ReuniaoDto reuniaoDto, UUID etapaId) {
        Optional<EtapaModel> etapa = etapaRepository.findById(etapaId);
        if (etapa.isEmpty()) {
            return Optional.empty();
        }

        ReuniaoModel novaReuniao = new ReuniaoModel();
        BeanUtils.copyProperties(reuniaoDto, novaReuniao);
        novaReuniao.setId(UUID.randomUUID());
        novaReuniao.setEtapa(etapa.get());

        return Optional.of(reuniaoRepository.save(novaReuniao));
    }

    public List<ReuniaoModel> getAllReunioes() {
        return reuniaoRepository.findAll();
    }

    public List<ReuniaoModel> getReunioesByEtapaId(UUID etapaId) {
        return reuniaoRepository.findByEtapaId(etapaId);
    }

    public boolean deleteReuniaoById(UUID id) {
        Optional<ReuniaoModel> reuniao = reuniaoRepository.findById(id);
        if (reuniao.isPresent()) {
            reuniaoRepository.delete(reuniao.get());
            return true;
        }
        return false;
    }

    public Optional<ReuniaoModel> updateReuniao(UUID id, ReuniaoDto reuniaoDto) {
        Optional<ReuniaoModel> reuniaoOptional = reuniaoRepository.findById(id);
        if (reuniaoOptional.isPresent()) {
            ReuniaoModel reuniao = reuniaoOptional.get();
            reuniao.setNomeReuniao(reuniaoDto.nomeReuniao());
            reuniao.setDataReuniao(reuniaoDto.dataReuniao());
            reuniao.setLocal(reuniaoDto.local());
            reuniao.setApontamentos(reuniaoDto.apontamentos());
            reuniaoRepository.save(reuniao);
            return Optional.of(reuniao);
        }
        return Optional.empty();
    }
}
