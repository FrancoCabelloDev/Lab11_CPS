package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para veterinarios
 */
@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {

    // Buscar por apellido
    List<Vet> findByLastName(String lastName);

    @Override
    List<Vet> findAll();
}
