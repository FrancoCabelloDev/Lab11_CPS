package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface OwnerMapper {
    Owner toOwner(OwnerDTO dto);

    OwnerDTO toOwnerDTO(Owner owner);

    List<OwnerDTO> toOwnerDTOList(List<Owner> owners);

    List<Owner> toOwnerList(List<OwnerDTO> ownerDTOs);
}
