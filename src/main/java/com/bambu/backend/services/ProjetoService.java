package com.bambu.backend.services;

import com.bambu.backend.models.EtapaModel;
import com.bambu.backend.models.ProjetoModel;
import com.bambu.backend.dtos.ProjetoDto;
import com.bambu.backend.models.ReuniaoModel;
import com.bambu.backend.repositories.AmbienteRepository;
import com.bambu.backend.repositories.EtapaRepository;
import com.bambu.backend.repositories.ProjetoRepository;
import com.bambu.backend.repositories.ReuniaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EtapaRepository etapaRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    public List<ProjetoModel> getAllProjects() {
        return projetoRepository.findAll();
    }

    public Optional<ProjetoModel> findById(UUID id) {
        return projetoRepository.findById(id);
    }

    public ProjetoModel criarProjeto(ProjetoDto projetoDto) {

        ProjetoModel projeto = new ProjetoModel();
        BeanUtils.copyProperties(projetoDto, projeto);

        float somaPorcentagens = projetoDto.porcentagemEtapa1() +
                projetoDto.porcentagemEtapa2() +
                projetoDto.porcentagemEtapa3() +
                projetoDto.porcentagemEtapa4() +
                projetoDto.porcentagemEtapa5();

        if (somaPorcentagens != 100) {
            throw new IllegalArgumentException("A soma das porcentagens das etapas deve ser igual a 100%.");
        }

        ProjetoModel projetoSalvo = projetoRepository.save(projeto);

        criarEtapa("Etapa 1 - Estudo Preliminar", projetoDto.porcentagemEtapa1(), projetoSalvo.getValorProjeto(), projetoSalvo);
        criarEtapa("Etapa 2 - Anteprojeto", projetoDto.porcentagemEtapa2(), projetoSalvo.getValorProjeto(), projetoSalvo);
        criarEtapa("Etapa 3 - Projeto Legal", projetoDto.porcentagemEtapa3(), projetoSalvo.getValorProjeto(), projetoSalvo);
        criarEtapa("Etapa 4 - Projeto Executivo", projetoDto.porcentagemEtapa4(), projetoSalvo.getValorProjeto(), projetoSalvo);
        criarEtapa("Etapa 5 - Orçamento e Planejamento", projetoDto.porcentagemEtapa5(), projetoSalvo.getValorProjeto(), projetoSalvo);

        return projetoSalvo;
    }

    private void criarEtapa(String nome, float porcentagem, float valorTotal, ProjetoModel projeto) {
        EtapaModel etapa = new EtapaModel();
        etapa.setNomeDaEtapa(nome);
        etapa.setDescricao("Descrição da " + nome);
        etapa.setValorEtapa((porcentagem / 100) * valorTotal);
        etapa.setDataDeInicio(projeto.getDataDeInicio());
        etapa.setDataPrevistaFim(projeto.getDataDeInicio());
        etapa.setProjeto(projeto);

        EtapaModel etapaSalva = etapaRepository.save(etapa);

        criarReunioesPadrao(etapaSalva);
    }

    private void criarReunioesPadrao(EtapaModel etapa) {
        ReuniaoModel reuniao1 = new ReuniaoModel();
        reuniao1.setNomeReuniao("Reunião 1");
        reuniao1.setDataReuniao(new Date());
        reuniao1.setLocal("Local padrão");
        reuniao1.setApontamentos("Apontamentos para a reunião");
        reuniao1.setEtapa(etapa);

        ReuniaoModel reuniao2 = new ReuniaoModel();
        reuniao2.setNomeReuniao("Reunião 2");
        reuniao2.setDataReuniao(new Date());
        reuniao2.setLocal("Local padrão");
        reuniao2.setApontamentos("Apontamentos para a reunião");
        reuniao2.setEtapa(etapa);

        reuniaoRepository.saveAll(List.of(reuniao1, reuniao2));
    }


    @Transactional
    public void deleteProjetoAndAmbientes(UUID projetoId) {

        etapaRepository.deleteByProjetoId(projetoId);
        ambienteRepository.deleteByProjetoId(projetoId);
        projetoRepository.deleteById(projetoId);
    }

    public ProjetoModel updateProjeto(UUID id, ProjetoDto projetoDto) {
        ProjetoModel projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado!"));

        BeanUtils.copyProperties(projetoDto, projeto);
        return projetoRepository.save(projeto);
    }

}
