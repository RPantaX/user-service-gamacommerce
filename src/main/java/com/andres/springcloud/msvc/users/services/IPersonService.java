package com.andres.springcloud.msvc.users.services;

import com.andres.springcloud.msvc.users.dto.PersonDto;
import com.andres.springcloud.msvc.users.dto.request.PersonRequest;

public interface IPersonService {
    PersonDto createPerson(PersonRequest personRequest);
    PersonDto getPersonById(Long personId);
    PersonDto updatePerson(Long personId, PersonDto personDto);
    void deletePerson(Long personId);
}
