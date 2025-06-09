package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.repositories.OwnerRepository;
import com.tecsup.petclinic.entities.Owner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CabelloOwnerUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUpdateOwnerSuccessfully() throws Exception {
        // 1. Obtener datos actuales del owner con ID 1
        MvcResult getResult = mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andReturn();

        String getResponse = getResult.getResponse().getContentAsString();
        OwnerTO owner = objectMapper.readValue(getResponse, OwnerTO.class);

        // 2. Modificar los campos deseados
        owner.setFirstName("Francis");
        owner.setCity("Ventanilla");

        // 3. Enviar actualización con PUT
        String requestJson = objectMapper.writeValueAsString(owner);
        log.info("📨 JSON enviado al endpoint (PUT): {}", requestJson);

        MvcResult putResult = mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Francis")))
                .andExpect(jsonPath("$.city", is("Ventanilla")))
                .andReturn();

        String putResponse = putResult.getResponse().getContentAsString();
        log.info("📥 Respuesta JSON recibida tras actualización: {}", putResponse);

        // 4. Validar cambio directamente en la base de datos
        Owner ownerBD = ownerRepository.findById(1).orElseThrow();
        assertEquals("Francis", ownerBD.getFirstName());
        assertEquals("Ventanilla", ownerBD.getCity());
        log.info("✅ Owner correctamente actualizado en base de datos.");
    }
}
