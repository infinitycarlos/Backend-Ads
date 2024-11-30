package com.bambu.backend.services;

import com.bambu.backend.dtos.ArquitetoDto;
import com.bambu.backend.exceptions.ArquitetoNotFoundException;
import com.bambu.backend.models.ArquitetoModel;
import com.bambu.backend.repositories.ArquitetoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArquitetoService {

    @Autowired
    private ArquitetoRepository arquitetoRepository;

    public ArquitetoModel createArquiteto(ArquitetoDto arquitetoDto) {
        ArquitetoModel arquitetoModel = new ArquitetoModel();
        BeanUtils.copyProperties(arquitetoDto, arquitetoModel);
        return arquitetoRepository.save(arquitetoModel);
    }

    public Optional<ArquitetoModel> getArquitetoById(UUID id) {

        Optional<ArquitetoModel> optionalArquitetoModel = arquitetoRepository.findById(id);

        if (optionalArquitetoModel.isEmpty()) {
            throw new ArquitetoNotFoundException(id);
        }

        return optionalArquitetoModel;
    }

    public List<ArquitetoModel> getAllArquitetos() {
        return arquitetoRepository.findAll();
    }

    public boolean deleteArquitetoById(UUID id) {
        Optional<ArquitetoModel> arquitetoModelOptional = arquitetoRepository.findById(id);
        if (arquitetoModelOptional.isEmpty()) {
            throw new ArquitetoNotFoundException(id);
        }

        arquitetoRepository.delete(arquitetoModelOptional.get());
        return true;
    }

    public Optional<ArquitetoModel> updateArquiteto(UUID id, ArquitetoDto arquitetoDto) {
        Optional<ArquitetoModel> arquitetoModelOptional = arquitetoRepository.findById(id);
        if (arquitetoModelOptional.isEmpty()) {
            throw new ArquitetoNotFoundException(id);
        }

        ArquitetoModel arquiteto = arquitetoModelOptional.get();
        BeanUtils.copyProperties(arquitetoDto, arquiteto);
        return Optional.of(arquitetoRepository.save(arquiteto));
    }

    public List<ArquitetoModel> getArquitetosByNome(String nome) {
        return arquitetoRepository.findByNomeContainingIgnoreCase(nome);
    }

}
