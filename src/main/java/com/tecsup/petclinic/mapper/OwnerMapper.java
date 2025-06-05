package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public Owner toEntity(OwnerTO dto) {
        Owner entity = new Owner();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setTelephone(dto.getTelephone());
        return entity;
    }

    public OwnerTO toTO(Owner entity) {
        OwnerTO dto = new OwnerTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setTelephone(entity.getTelephone());
        return dto;
    }
}
