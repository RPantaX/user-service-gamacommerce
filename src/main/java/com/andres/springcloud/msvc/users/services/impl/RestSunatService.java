package com.andres.springcloud.msvc.users.services.impl;

import com.andres.springcloud.msvc.users.dto.response.ResponseSunat;
import com.andres.springcloud.msvc.users.rest.client.ClienteSunat;
import com.andres.springcloud.msvc.users.services.IRestSunatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestSunatService implements IRestSunatService {
    private final ClienteSunat sunat;
    @Value("${token.api}")
    private String tokenApi;
    @Override
    public ResponseSunat getInfoSunat(String numDoc) {
        String authorization = "Bearer " + tokenApi;
        return sunat.getInfoSunat(numDoc,authorization);
    }

}
