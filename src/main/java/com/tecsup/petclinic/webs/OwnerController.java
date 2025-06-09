package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.services.OwnerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerMapper mapper;

    public OwnerController(OwnerService ownerService, OwnerMapper mapper) {
        this.ownerService = ownerService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<OwnerTO>> findAllOwners() {
        List<Owner> owners = ownerService.findAll();
        List<OwnerTO> ownerTOs = mapper.toOwnerTOList(owners);
        return ResponseEntity.ok(ownerTOs);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OwnerTO> create(@RequestBody OwnerTO ownerTO) {
        Owner newOwner = mapper.toOwner(ownerTO);
        OwnerTO createdOwner = mapper.toOwnerTO(ownerService.create(newOwner));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerTO> findById(@PathVariable Integer id) {
        try {
            Owner owner = ownerService.findById(id);
            return ResponseEntity.ok(mapper.toOwnerTO(owner));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerTO> update(@RequestBody OwnerTO ownerTO, @PathVariable Integer id) {
        try {
            Owner existingOwner = ownerService.findById(id);
            existingOwner.setFirstName(ownerTO.getFirstName());
            existingOwner.setLastName(ownerTO.getLastName());
            existingOwner.setAddress(ownerTO.getAddress());
            existingOwner.setCity(ownerTO.getCity());
            existingOwner.setTelephone(ownerTO.getTelephone());

            Owner updatedOwner = ownerService.update(existingOwner);
            return ResponseEntity.ok(mapper.toOwnerTO(updatedOwner));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.ok("Deleted Owner with ID: " + id);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
