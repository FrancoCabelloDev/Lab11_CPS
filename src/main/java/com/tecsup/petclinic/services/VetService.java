package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;
import com.tecsup.petclinic.entities.Vet;

public interface VetService {

    Vet save(Vet vet);
    Optional<Vet> findById(Long id);
    List<Vet> findAll();
    void deleteById(Long id);
}
