package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para Veterinarios
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VetDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    // IDs de especialidades asociadas (opcional, si necesitas incluirlo)
    private List<Integer> specialties;
}
