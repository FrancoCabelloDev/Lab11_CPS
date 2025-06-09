package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-08T22:14:57-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class OwnerMapperImpl implements OwnerMapper {

    @Override
    public Owner toOwner(OwnerTO dto) {

        Owner owner = new Owner();

        if ( dto != null ) {
            owner.setId( dto.getId() );
            owner.setFirstName( dto.getFirstName() );
            owner.setLastName( dto.getLastName() );
            owner.setAddress( dto.getAddress() );
            owner.setCity( dto.getCity() );
            owner.setTelephone( dto.getTelephone() );
        }

        return owner;
    }

    @Override
    public OwnerTO toOwnerTO(Owner owner) {

        OwnerTO ownerTO = new OwnerTO();

        if ( owner != null ) {
            ownerTO.setId( owner.getId() );
            ownerTO.setFirstName( owner.getFirstName() );
            ownerTO.setLastName( owner.getLastName() );
            ownerTO.setAddress( owner.getAddress() );
            ownerTO.setCity( owner.getCity() );
            ownerTO.setTelephone( owner.getTelephone() );
        }

        return ownerTO;
    }

    @Override
    public List<OwnerTO> toOwnerTOList(List<Owner> ownerList) {
        if ( ownerList == null ) {
            return new ArrayList<OwnerTO>();
        }

        List<OwnerTO> list = new ArrayList<OwnerTO>( ownerList.size() );
        for ( Owner owner : ownerList ) {
            list.add( toOwnerTO( owner ) );
        }

        return list;
    }

    @Override
    public List<Owner> toOwnerList(List<OwnerTO> dtoList) {
        if ( dtoList == null ) {
            return new ArrayList<Owner>();
        }

        List<Owner> list = new ArrayList<Owner>( dtoList.size() );
        for ( OwnerTO ownerTO : dtoList ) {
            list.add( toOwner( ownerTO ) );
        }

        return list;
    }
}
