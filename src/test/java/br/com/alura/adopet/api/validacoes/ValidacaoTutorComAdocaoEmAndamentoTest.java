package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {


    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Adocao adocao;

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacao;


    @Test
    void naoDevePermitirAdocaoComStatusAguardandoAvaliacao() {
        Tutor tutor = new Tutor(new CadastroTutorDto("Jose", "85985335879", "jose@gmail.com"));
        Adocao adocao = new Adocao(tutor, pet, "Motivo");

        List<Adocao> adocoes = List.of(adocao);

        given(adocaoRepository.findAll()).willReturn(adocoes);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

    @Test
    void devePermitirAdocaoParaTutor() {
        List<Adocao> adocoes = List.of(adocao);
        given(adocaoRepository.findAll()).willReturn(adocoes);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }
}