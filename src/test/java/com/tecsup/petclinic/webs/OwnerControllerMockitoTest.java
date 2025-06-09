package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private OwnerMapper ownerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetOwnerById() throws Exception {
        Owner mockOwner = new Owner(1, "Carlos", "Quispe", "Lima", "Lima", "987654321");
        OwnerTO mockOwnerTO = new OwnerTO(1, "Carlos", "Quispe", "Lima", "Lima", "987654321");

        Mockito.when(ownerService.findById(1)).thenReturn(mockOwner);
        Mockito.when(ownerMapper.toOwnerTO(mockOwner)).thenReturn(mockOwnerTO);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Carlos")))
                .andExpect(jsonPath("$.lastName", is("Quispe")))
                .andExpect(jsonPath("$.city", is("Lima")));
    }
}

