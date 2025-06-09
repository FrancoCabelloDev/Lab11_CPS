package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.repositories.OwnerRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateNewOwnerSuccessfully() throws Exception {
        // Datos del dueño a crear
        OwnerTO newOwner = new OwnerTO(null, "Franco", "Cabello", "Av. Perú 134", "Chosica", "987354321");
        String requestJson = objectMapper.writeValueAsString(newOwner);
        log.info("JSON enviado al endpoint: {}", requestJson);

        // Envío de la solicitud POST y validaciones del JSON de respuesta
        MvcResult result = mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Franco")))
                .andExpect(jsonPath("$.city", is("Chosica")))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        log.info("Respuesta JSON recibida: {}", responseJson);

        // Validar en la BD
        boolean exists = ownerRepository.findAll()
                .stream()
                .anyMatch(o -> o.getFirstName().equals("Franco") && o.getCity().equals("Chosica"));

        assertTrue(exists);
        log.info("Owner correctamente guardado en base de datos.");
    }
}
