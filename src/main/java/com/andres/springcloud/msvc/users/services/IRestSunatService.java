package com.andres.springcloud.msvc.users.services;

import com.andres.springcloud.msvc.users.dto.response.ResponseSunat;

public interface IRestSunatService {
    ResponseSunat getInfoSunat(String numDoc);
}
