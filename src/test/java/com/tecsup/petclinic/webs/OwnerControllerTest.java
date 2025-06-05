package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/owners";

    @Test
    void testCreateOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Carlos");
        owner.setLastName("Quispe");
        owner.setAddress("Av. Perú 123");
        owner.setCity("Lima");
        owner.setTelephone("987654321");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Owner> request = new HttpEntity<>(owner, headers);

        ResponseEntity<Owner> response = restTemplate.postForEntity(baseUrl, request, Owner.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("Carlos");
    }

    @Test
    void testFindOwnerById() {
        Long id = 1L;

        ResponseEntity<Owner> response = restTemplate.getForEntity(baseUrl + "/" + id, Owner.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
    }
}