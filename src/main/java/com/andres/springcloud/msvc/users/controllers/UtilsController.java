package com.andres.springcloud.msvc.users.controllers;

import com.andres.springcloud.msvc.users.services.IUtilsService;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-service/utils")
@RequiredArgsConstructor
public class UtilsController {
    private final IUtilsService utilsService;
    @GetMapping("/list/document-types")
    public ResponseEntity<ApiResponse> listAllDocumentTypes(){
        return ResponseEntity.ok(ApiResponse.ok("List of document types retrieved successfully",
                utilsService.getAllDocumentTypes()));
    }
    @GetMapping("/list/company-types")
    public ResponseEntity<ApiResponse> listAllCompanyTypes(){
        return ResponseEntity.ok(ApiResponse.ok("List of company types retrieved successfully",
                utilsService.getAllCompanyTypes()));
    }
    @GetMapping("/list/contract-kinds")
    public ResponseEntity<ApiResponse> listAllContractKinds(){
        return ResponseEntity.ok(ApiResponse.ok("List of contract kinds retrieved successfully",
                utilsService.getAllContractKinds()));
    }
}
