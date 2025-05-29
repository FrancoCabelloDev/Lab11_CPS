package com.tecsup.petclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.petclinic.entities.Vet;

public interface VetRepository extends JpaRepository<Vet, Long> { }
