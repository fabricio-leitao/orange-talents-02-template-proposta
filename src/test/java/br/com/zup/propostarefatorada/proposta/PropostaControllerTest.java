package br.com.zup.propostarefatorada.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@EnableScheduling
class PropostaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private ObjectMapper mapper;
    @PersistenceContext
    private EntityManager manager;

    private String toJson(Object form) throws JsonProcessingException {
        return mapper.writeValueAsString(form);
    }

    @Test
    public void deveCriarPropostaERetornar201() throws Exception {
        NovaPropostaRequest propostaComCPFRequest = new NovaPropostaRequest("752.139.060-10", "gokudera@email.com", "Gokudera", "Rua dos Cravos 777", new BigDecimal(2000));
        NovaPropostaRequest propostaComCNPJRequest = new NovaPropostaRequest("64.029.381/0001-14", "todoroki@email.com", "Todoroki", "Rua das Rosas 234", new BigDecimal(2000));

        String locationComCPF = mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(propostaComCPFRequest)))
                .andExpect(status().is(201)).andReturn().getResponse().getHeader("Location");

        String locationComCNPJ = mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(propostaComCNPJRequest)))
                .andExpect(status().is(201)).andReturn().getResponse().getHeader("Location");


        Proposta propostaComCPF = propostaRepository.findByEmail(propostaComCPFRequest.getEmail());
        Proposta propostaComCNPJ = propostaRepository.findByEmail(propostaComCNPJRequest.getEmail());

        assertEquals(locationComCPF, "http://localhost/api/propostas/" + propostaComCPF.getId());
        assertEquals(locationComCNPJ, "http://localhost/api/propostas/" + propostaComCNPJ.getId());
        assertNotNull(propostaComCPF);
        assertNotNull(propostaComCNPJ);
        assertEquals(propostaComCPF.getDocumento(), propostaComCPFRequest.getDocumento());
        assertEquals(propostaComCNPJ.getDocumento(), propostaComCNPJRequest.getDocumento());
    }

    @Test
    public void naoDeveCriarPropostaComCPFOuCNPJInavlidos() throws Exception {
        NovaPropostaRequest propostaComCPFRequest = new NovaPropostaRequest("752.139.060-11", "gokudera@email.com", "Gokudera", "Rua dos Cravos 777", new BigDecimal(2000));
        NovaPropostaRequest propostaComCNPJRequest = new NovaPropostaRequest("64.029.381/0201-14", "todoroki@email.com", "Todoroki", "Rua das Rosas 234", new BigDecimal(2000));

        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(propostaComCPFRequest)))
                .andExpect(status().is(400));

        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(propostaComCNPJRequest)))
                .andExpect(status().is(400));
    }

    @Test
    public void naoDeveCriarPropostaComCPFOuCNPJDuplicados() throws Exception {
        NovaPropostaRequest propostaComCPFRequest = new NovaPropostaRequest("752.139.060-11", "gokudera@email.com", "Gokudera", "Rua dos Cravos 777", new BigDecimal(2000));
        NovaPropostaRequest propostaComCNPJRequest = new NovaPropostaRequest("64.029.381/0201-14", "todoroki@email.com", "Todoroki", "Rua das Rosas 234", new BigDecimal(2000));

        propostaRepository.save(propostaComCPFRequest.toModel());
        propostaRepository.save(propostaComCNPJRequest.toModel());

        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(propostaComCPFRequest)))
                .andExpect(status().is(422));

        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(propostaComCNPJRequest)))
                .andExpect(status().is(422));
    }
}