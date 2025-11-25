package com.andres.springcloud.msvc.users.rest.client;

import com.andres.springcloud.msvc.users.dto.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client-sunat", url = "https://api.decolecta.com/v1/sunat")
public interface ClienteSunat {
    @GetMapping("/ruc")
    ResponseSunat getInfoSunat(@RequestParam("numero") String numero,
                               @RequestHeader("Authorization") String authorizationHeader);
}
