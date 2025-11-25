package com.andres.springcloud.msvc.users.services;

import com.andres.springcloud.msvc.users.dto.AddressDto;
import com.andres.springcloud.msvc.users.dto.PersonDto;
import com.andres.springcloud.msvc.users.dto.request.CompanyRequest;
import com.andres.springcloud.msvc.users.dto.request.CreateCompanyRequest;
import com.andres.springcloud.msvc.users.dto.request.PersonRequest;
import com.andres.springcloud.msvc.users.dto.response.ResponseSunat;

public interface IAddressService {
    AddressDto createAddress(AddressDto addressDto);
    AddressDto createPersonAddress(PersonRequest personRequest);
    AddressDto createCompanyAddress(ResponseSunat responseSunat);
    AddressDto getAddressById(Long addressId);
    AddressDto updateAddress(Long addressId, AddressDto addressDto);
    void deleteAddress(Long addressId);
}
