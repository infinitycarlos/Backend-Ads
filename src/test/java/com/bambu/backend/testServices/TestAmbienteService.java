package com.bambu.backend.testServices;

import com.bambu.backend.dtos.AmbienteDto;
import com.bambu.backend.dtos.ProjetoDto;
import com.bambu.backend.models.AmbienteModel;
import com.bambu.backend.models.ProjetoModel;
import com.bambu.backend.repositories.AmbienteRepository;
import com.bambu.backend.services.AmbienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestAmbienteService {
    @Mock
    private AmbienteRepository ambienteRepository;

    @InjectMocks
    private AmbienteService ambienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarAmbientes_sucesso() {
        AmbienteDto ambienteDto = new AmbienteDto("Quarto", 5);
        AmbienteDto ambienteDto1 = new AmbienteDto("Sala", 9);

        List<AmbienteDto> ambienteDtos = Arrays.asList(ambienteDto, ambienteDto1);

        ProjetoModel projeto = new ProjetoModel();

        ambienteService.salvarAmbientes(ambienteDtos, projeto);

        verify(ambienteRepository, times(2)).save(any(AmbienteModel.class));
    }

    @Test
    void findAmbientesByProjetoId_sucesso() {
        UUID projetoId = UUID.randomUUID();
        AmbienteModel ambiente = new AmbienteModel();
        ambiente.setNome("Sala");
        ambiente.setTamanho(20.0f);

        AmbienteModel ambiente1 = new AmbienteModel();
        ambiente1.setNome("Quarto");
        ambiente1.setTamanho(20.0f);

        List<AmbienteModel> ambientes = Arrays.asList(ambiente, ambiente1);
        when(ambienteRepository.findByProjetoId(projetoId)).thenReturn(ambientes);

        List<AmbienteDto> resultado = ambienteService.findAmbientesByProjetoId(projetoId);

        assertEquals(2, resultado.size());
        assertEquals("Sala", resultado.get(0).nome());
        assertEquals("Quarto", resultado.get(1).nome());
        assertEquals(20.0f, resultado.get(1).tamanho());
        assertEquals(20.0f, resultado.get(1).tamanho());

        verify(ambienteRepository, times(1)).findByProjetoId(projetoId);
    }
}
