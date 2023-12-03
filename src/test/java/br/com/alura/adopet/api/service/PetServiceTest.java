package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository repository;

    @InjectMocks
    private PetService petService;

    @Mock
    private CadastroPetDto cadastroPetDto;

    @Mock
    private Abrigo abrigo;

    @Test
    void deveRetornarListaPetsDisponiveis() {
        repository.findAllByAdotadoFalse();

        then(repository).should().findAllByAdotadoFalse();
    }

    @Test
    void deveriaCadastrarPet() {
        //Act
        petService.cadastrarPet(abrigo,cadastroPetDto);

        //Assert
        then(repository).should().save(new Pet(cadastroPetDto,abrigo));
    }
}