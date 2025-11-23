package com.andres.springcloud.msvc.users.services.impl;

import com.andres.springcloud.msvc.users.dto.AddressDto;
import com.andres.springcloud.msvc.users.dto.PersonDto;
import com.andres.springcloud.msvc.users.dto.request.PersonRequest;
import com.andres.springcloud.msvc.users.entities.Address;
import com.andres.springcloud.msvc.users.entities.DocumentType;
import com.andres.springcloud.msvc.users.entities.Person;
import com.andres.springcloud.msvc.users.repositories.AddressRepository;
import com.andres.springcloud.msvc.users.repositories.DocumentTypeRepository;
import com.andres.springcloud.msvc.users.repositories.PersonRepository;
import com.andres.springcloud.msvc.users.services.IAddressService;
import com.andres.springcloud.msvc.users.services.IPersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.Constants;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService implements IPersonService {
    private final IAddressService addressService;
    private final PersonRepository personRepository;

    //JUST FOR JPA CONEXT
    private final AddressRepository addressRepository;
    private final DocumentTypeRepository documentTypeRepository;

    @Override
    @Transactional
    public PersonDto createPerson(PersonRequest personRequest) {
        log.info("Creating person with data: {}", personRequest);
        AddressDto addressDtoSaved =  addressService.createPersonAddress(personRequest);
        Person personSaved = personRepository.save(buildPersonEntity(personRequest, addressDtoSaved.getId()));
        return mapToPersonDto(personSaved);
    }

    @Override
    public PersonDto getPersonById(Long personId) {
        return null;
    }

    @Override
    public PersonDto updatePerson(Long personId, PersonDto personDto) {
        return null;
    }

    @Override
    public void deletePerson(Long personId) {

    }
    private Person buildPersonEntity(PersonRequest personRequest, Long addressSavedId) {
        return Person.builder()
                .name(personRequest.getPersonName().toUpperCase())
                .lastName(personRequest.getPersonLastName().toUpperCase())
                .phoneNumber(personRequest.getPersonPhoneNumber().toUpperCase())
                .emailAddress(personRequest.getPersonEmailAddress().toUpperCase())
                .address(addressRepository.getReferenceById(addressSavedId))
                .documentNumber(personRequest.getPersonDocumentNumber())
                .documentType(DocumentType.builder().id(personRequest.getPersonDocumentId()).build())
                .state(Constants.STATUS_ACTIVE)
                .createdAt(Constants.getTimestamp())
                .modifiedByUser(!com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession().isEmpty() ? com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession() : "SYSTEM")
                .build();
    }
    public static PersonDto mapToPersonDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .lastName(person.getLastName())
                .phoneNumber(person.getPhoneNumber())
                .emailAddress(person.getEmailAddress())
                .addressId(person.getAddress().getId())
                .documentTypeId(person.getDocumentType().getId())
                .documentNumber(person.getDocumentNumber())
                .build();
    }
}
