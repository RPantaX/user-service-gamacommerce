package com.andres.springcloud.msvc.users.services.impl;

import com.andres.springcloud.msvc.users.dto.AddressDto;
import com.andres.springcloud.msvc.users.dto.request.PersonRequest;
import com.andres.springcloud.msvc.users.dto.response.ResponseSunat;
import com.andres.springcloud.msvc.users.entities.Address;
import com.andres.springcloud.msvc.users.repositories.AddressRepository;
import com.andres.springcloud.msvc.users.services.IAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.Constants;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    @Override
    public AddressDto createAddress(AddressDto addressDto) {
        return null;
    }

    @Override
    @Transactional
    public AddressDto createPersonAddress(PersonRequest personRequest) {
        AddressDto addressDto = buildAddressFromPersonRequest(personRequest);
        Address address = addressRepository.save(mapToAddressEntity(addressDto));
        return mapToAddressDto(address);
    }

    @Override
    @Transactional
    public AddressDto createCompanyAddress(ResponseSunat responseSunat) {
        AddressDto addressDto = buildAddressFromSunat(responseSunat);
        Address address = addressRepository.save(mapToAddressEntity(addressDto));
        return mapToAddressDto(address);
    }

    @Override
    public AddressDto getAddressById(Long addressId) {
        return null;
    }

    @Override
    public AddressDto updateAddress(Long addressId, AddressDto addressDto) {
        return null;
    }

    @Override
    public void deleteAddress(Long addressId) {

    }
    private AddressDto buildAddressFromSunat(ResponseSunat responseSunat) {
        return AddressDto.builder()
                .city(responseSunat.getDistrito().toUpperCase())
                .country("PERU") // Assuming the country is Peru
                .state(responseSunat.getEstado().toUpperCase())
                .postalCode(responseSunat.getUbigeo().toUpperCase()) // Sunat API may not provide postal code
                .street(responseSunat.getDireccion().toUpperCase())
                .build();
    }
    private AddressDto buildAddressFromPersonRequest(PersonRequest personRequest) {
        return AddressDto.builder()
                .city(personRequest.getPersonAddressCity().toUpperCase())
                .country(personRequest.getPersonAddressCountry().toUpperCase())
                .state(personRequest.getPersonAddressState().toUpperCase())
                .postalCode(personRequest.getPersonAddressPostalCode().toUpperCase())
                .street(personRequest.getPersonAddressStreet().toUpperCase())
                .build();
    }
    private Address mapToAddressEntity(AddressDto addressDto) {
        return Address.builder()
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .state(addressDto.getState())
                .postalCode(addressDto.getPostalCode())
                .street(addressDto.getStreet())
                .createdAt(Constants.getTimestamp())
                .modifiedByUser(!com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession().isEmpty() ? com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession() : "SYSTEM")
                .build();
    }
    public static AddressDto mapToAddressDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .street(address.getStreet())
                .build();
    }
}
