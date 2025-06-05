package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VetServiceTest {

    @Autowired
    private VetService vetService;

    private static Long vetId;

    @Test
    void testCreateVet() {
        Vet v = new Vet();
        v.setFirstName("Francis");
        v.setLastName("Pomasoncco");
        Vet saved = vetService.save(v);
        assertNotNull(saved.getId(), "El ID no debe ser nulo tras guardar");
        vetId = saved.getId();
    }

}
