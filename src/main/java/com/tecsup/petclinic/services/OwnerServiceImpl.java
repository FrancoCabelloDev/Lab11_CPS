package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public OwnerDTO create(Owner dto) {
        Owner owner = ownerMapper.toOwner(dto);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.toOwnerDTO(savedOwner);
    }

    @Override
    public OwnerDTO update(OwnerDTO dto) throws OwnerNotFoundException {
        Optional<Owner> optionalOwner = ownerRepository.findById(dto.getId());

        if (optionalOwner.isEmpty()) {
            throw new OwnerNotFoundException("Owner ID not found: " + dto.getId());
        }

        Owner owner = optionalOwner.get();
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setAddress(dto.getAddress());
        owner.setCity(dto.getCity());
        owner.setTelephone(dto.getTelephone());

        return ownerMapper.toOwnerDTO(ownerRepository.save(owner));
    }

    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        if (!ownerRepository.existsById(id)) {
            throw new OwnerNotFoundException("Owner ID not found: " + id);
        }
        ownerRepository.deleteById(id);
    }

    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isEmpty()) {
            throw new OwnerNotFoundException("Owner ID not found: " + id);
        }
        return ownerMapper.toOwnerDTO(optionalOwner.get());
    }

    @Override
    public List<OwnerDTO> findAll() {
        return ownerRepository.findAll().stream()
                .map(ownerMapper::toOwnerDTO)
                .collect(Collectors.toList());
    }
}
