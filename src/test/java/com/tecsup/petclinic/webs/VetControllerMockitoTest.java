package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class VetControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VetRepository vetRepository;

    @MockBean
    private VetService vetService;

    VetMapper mapper = Mappers.getMapper(VetMapper.class);

    @Test
    public void testFindAllVets() throws Exception {
        List<VetDTO> vetDTOs = Arrays.asList(
                new VetDTO(1, "James", "Carter", null),
                new VetDTO(2, "Helen", "Leary", null)
        );

        List<Vet> vets = mapper.toVetList(vetDTOs);

        Mockito.when(vetService.findAll()).thenReturn(vets);

        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].lastName", is("Leary")));
    }

    @Test
    public void testFindVetOK() throws Exception {
        VetDTO vetDTO = new VetDTO(1, "James", "Carter", null);
        Vet vet = mapper.toVet(vetDTO);

        Mockito.when(vetService.findById(1)).thenReturn(vet);

        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("James")))
                .andExpect(jsonPath("$.lastName", is("Carter")));
    }

    @Test
    public void testFindVetKO() throws Exception {
        Mockito.when(vetService.findById(999))
                .thenThrow(new VetNotFoundException("Vet not found"));

        mockMvc.perform(get("/vets/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVet() throws Exception {
        VetDTO newVetDTO = new VetDTO(null, "Linda", "Douglas", null);
        Vet vet = mapper.toVet(newVetDTO);
        vet.setId(10);

        Mockito.when(vetService.create(Mockito.any(Vet.class))).thenReturn(vet);

        mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Linda")))
                .andExpect(jsonPath("$.lastName", is("Douglas")));
    }

    @Test
    public void testDeleteVet() throws Exception {
        Mockito.doNothing().when(vetService).delete(5);

        mockMvc.perform(delete("/vets/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVetKO() throws Exception {
        Mockito.doThrow(new VetNotFoundException("Not found")).when(vetService).delete(999);

        mockMvc.perform(delete("/vets/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateVet() throws Exception {
        VetDTO originalDTO = new VetDTO(7, "Rafael", "Ortega", null);
        VetDTO updatedDTO = new VetDTO(7, "Rafael", "Ramirez", null);

        Vet originalVet = mapper.toVet(originalDTO);
        Vet updatedVet = mapper.toVet(updatedDTO);

        Mockito.when(vetService.findById(7)).thenReturn(originalVet);
        Mockito.when(vetService.update(Mockito.any(Vet.class))).thenReturn(updatedVet);

        mockMvc.perform(put("/vets/7")
                        .content(om.writeValueAsString(updatedDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Ramirez")));
    }
}
