package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.services.OwnerService;
import com.tecsup.petclinic.util.TObjectCreator;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    private final ObjectMapper om = new ObjectMapper();

    OwnerMapper mapper = Mappers.getMapper(OwnerMapper.class);

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void testFindAllOwners() throws Exception {
        List<OwnerDTO> ownerDTOs = TObjectCreator.getAllOwnerDTOs();
        List<Owner> owners = mapper.toOwnerList(ownerDTOs);

        Mockito.when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(ownerDTOs.size())))
                .andExpect(jsonPath("$[0].firstName", is(ownerDTOs.get(0).getFirstName())));
    }

    @Test
    public void testFindOwnerOK() throws Exception {
        OwnerDTO dto = TObjectCreator.getOwnerDTO();
        Owner owner = mapper.toOwner(dto);

        Mockito.when(ownerService.findById(dto.getId())).thenReturn(owner);

        mockMvc.perform(get("/owners/" + dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.city", is(dto.getCity())));
    }

    @Test
    public void testFindOwnerKO() throws Exception {
        Integer ID_NOT_FOUND = 999;

        Mockito.when(ownerService.findById(ID_NOT_FOUND))
                .thenThrow(new OwnerNotFoundException("Owner not found"));

        mockMvc.perform(get("/owners/" + ID_NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOwner() throws Exception {
        OwnerDTO newOwnerDTO = TObjectCreator.newOwnerDTO();
        Owner newOwner = mapper.toOwner(newOwnerDTO);

        Mockito.when(ownerService.create(newOwner)).thenReturn(newOwner);

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(newOwnerDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(newOwnerDTO.getLastName())));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        OwnerDTO newOwnerDTO = TObjectCreator.newOwnerDTOForDelete();
        Owner newOwner = mapper.toOwner(newOwnerDTO);

        Mockito.when(ownerService.create(newOwner)).thenReturn(newOwner);

        ResultActions result = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = result.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        Mockito.doNothing().when(ownerService).delete(id);

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }
}
