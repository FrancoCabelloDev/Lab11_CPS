package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para veterinarios
 */
@RestController
@Slf4j
public class VetController {

    private final VetService vetService;
    private final VetMapper mapper;

    public VetController(VetService vetService, VetMapper mapper) {
        this.vetService = vetService;
        this.mapper = mapper;
    }

    /**
     * Listar todos los veterinarios
     */
    @GetMapping("/vets")
    public ResponseEntity<List<VetDTO>> findAllVets() {
        List<Vet> vets = vetService.findAll();
        List<VetDTO> vetsDTO = mapper.toVetDTOList(vets);
        return ResponseEntity.ok(vetsDTO);
    }

    /**
     * Crear nuevo veterinario
     */
    @PostMapping("/vets")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetDTO> create(@RequestBody VetDTO vetDTO) {
        Vet vet = mapper.toVet(vetDTO);
        VetDTO createdVetDTO = mapper.toVetDTO(vetService.create(vet));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVetDTO);
    }

    /**
     * Buscar veterinario por ID
     */
    @GetMapping("/vets/{id}")
    public ResponseEntity<VetDTO> findById(@PathVariable Integer id) {
        try {
            Vet vet = vetService.findById(id);
            return ResponseEntity.ok(mapper.toVetDTO(vet));
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualizar veterinario por ID
     */
    @PutMapping("/vets/{id}")
    public ResponseEntity<VetDTO> update(@RequestBody VetDTO vetDTO, @PathVariable Integer id) {
        try {
            Vet vet = vetService.findById(id);
            vet.setFirstName(vetDTO.getFirstName());
            vet.setLastName(vetDTO.getLastName());
            vetService.update(vet);
            return ResponseEntity.ok(mapper.toVetDTO(vet));
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar veterinario por ID
     */
    @DeleteMapping("/vets/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok("Deleted vet with ID: " + id);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
