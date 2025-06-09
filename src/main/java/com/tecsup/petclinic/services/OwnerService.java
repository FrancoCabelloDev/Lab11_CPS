package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

import java.util.List;

public interface OwnerService {
    OwnerDTO create(Owner dto);
    OwnerDTO update(OwnerDTO dto) throws OwnerNotFoundException;
    void delete(Integer id) throws OwnerNotFoundException;
    OwnerDTO findById(Integer id) throws OwnerNotFoundException;
    List<OwnerDTO> findAll();
}
