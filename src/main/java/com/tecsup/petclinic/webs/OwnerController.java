package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity<Owner> create(@RequestBody Owner owner) {
        Owner savedOwner = ownerService.create(owner);
        return ResponseEntity.status(201).body(savedOwner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> findById(@PathVariable Long id) {
        Owner owner = ownerService.findById(id);
        return ResponseEntity.ok(owner);
    }

    @GetMapping
    public List<Owner> findAll() {
        return ownerService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
