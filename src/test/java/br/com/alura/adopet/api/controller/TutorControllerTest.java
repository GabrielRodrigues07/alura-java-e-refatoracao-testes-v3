package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorService tutorService;


    @Test
    void deveReceber200AoCadastrarTutor() throws Exception {
        String json = """
                {
                    "nome": "Fulano da Silva",
                    "telefone": "61900000000",
                    "email": "fulano@email.com.br"
                }
                """;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deveReceber400AoCadastrarTutorDadosInvalidos() throws Exception {
        String json = """
                {
                    "nome": "Fulano da Silva",
                    "telefone": "619000000000",
                    "email": "fulano@email.com.br"
                }
                """;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeAtualizarTutor() throws Exception {
        String json = """
                {
                    "id": 1,
                    "nome": "Fulano da Silva",
                    "telefone": "61900000000",
                    "email": "fulano@email.com.br"
                }
                """;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeAtualizarTutor() throws Exception {
        String json = """
                {
                    "nome": "Fulano da Silva",
                    "telefone": "61900000000",
                    "email": "fulano@email.com.br"
                }
                """;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}