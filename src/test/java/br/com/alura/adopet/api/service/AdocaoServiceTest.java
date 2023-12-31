package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AdocaoService service;

    @Mock
    private Pet pet;
    @Mock
    private Abrigo abrigo;
    @Mock
    private Tutor tutor;

    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();
    @Mock
    private ValidacaoSolicitacaoAdocao validador1;
    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    void deveriaSalvarAdocaoAoSolicitar() {

        //ARRANGE
        this.solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(10L, 20L, "motivo qualquer");
        given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        service.solicitar(solicitacaoAdocaoDto);

        //ASSERT
        BDDMockito.then(adocaoRepository).should().save(adocaoCaptor.capture());

        Adocao adocaoSalva = adocaoCaptor.getValue();

        assertEquals(pet, adocaoSalva.getPet());
        assertEquals(tutor, adocaoSalva.getTutor());
        assertEquals(solicitacaoAdocaoDto.motivo(), adocaoSalva.getMotivo());

    }

    @Test
    void deveriaChamarValidadoresDeAdocaoAoSolicitar() {

        //ARRANGE
        this.solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(10L, 20L, "motivo qualquer");
        given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.addAll(Arrays.asList(validador1, validador2));

        //ACT
        service.solicitar(solicitacaoAdocaoDto);

        //ASSERT
        then(validador1).should().validar(solicitacaoAdocaoDto);
        then(validador2).should().validar(solicitacaoAdocaoDto);

    }

}