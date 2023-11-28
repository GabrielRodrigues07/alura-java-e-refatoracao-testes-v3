package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdocaoService service;

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                        post("/adocoes")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();

        //ASSERT
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaSolicitacaoDeAdocao() throws Exception {
        //ARRANGE
        String json = """
                {
                    "idPet": 2,
                    "idTutor": 2,
                    "motivo": "Motivo qualquer"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                        post("/adocoes")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();

        //ASSERT
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaAprovacaoDeAdocao() throws Exception {
        //ARRANGE
        String json = """
                {
                    "idAdocao": 1
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                        put("/adocoes/aprovar")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();

        //ASSERT
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaReprovacaoDeAdocao() throws Exception {
        //ARRANGE
        String json = """
                {
                    "idAdocao": 1,
                    "justificativa": "Justificativa qualquer"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                        put("/adocoes/reprovar")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();

        //ASSERT
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}