package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {


    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Abrigo abrigo;

    @Mock
    private CadastroAbrigoDto dto;

    @InjectMocks
    private AbrigoService abrigoService;

    @Test
    void deveRetornarListaDeAbrigos() {
        abrigoService.listar();

        then(abrigoRepository).should().findAll();
    }

    @Test
    void deveCadastrarAbrigo() {
        abrigoService.cadatrar(dto);

        then(abrigoRepository).should().save(Mockito.any(Abrigo.class));
    }


    @Test
    void naoDeveCadastrarAbrigo() {
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.cadatrar(dto));
    }

    @Test
    void deveriaChamarListaDePetsDoAbrigoAtravesDoNome() {
        //Arrange
        String nome = "Miau";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //Act
        abrigoService.listarPetsDoAbrigo(nome);

        //Assert
        then(petRepository).should().findByAbrigo(abrigo);
    }

}