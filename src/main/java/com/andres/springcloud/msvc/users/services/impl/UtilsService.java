package com.andres.springcloud.msvc.users.services.impl;

import com.andres.springcloud.msvc.users.dto.CompanyTypeDto;
import com.andres.springcloud.msvc.users.dto.ContractKindDto;
import com.andres.springcloud.msvc.users.dto.DocumentTypeDto;
import com.andres.springcloud.msvc.users.entities.ContractKind;
import com.andres.springcloud.msvc.users.repositories.CompanyTypeRepository;
import com.andres.springcloud.msvc.users.repositories.ContractKindRepository;
import com.andres.springcloud.msvc.users.repositories.DocumentTypeRepository;
import com.andres.springcloud.msvc.users.services.IUtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class UtilsService implements IUtilsService {

    private final ContractKindRepository contractKindRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final CompanyTypeRepository companyTypeRepository;


    @Override
    public List<CompanyTypeDto> getAllCompanyTypes() {
        return companyTypeRepository.findAll()
                .stream()
                .map(companyType -> CompanyTypeDto.builder()
                        .id(companyType.getId())
                        .value(companyType.getValue())
                        .build())
                .toList();
    }

    @Override
    public List<DocumentTypeDto> getAllDocumentTypes() {
        return documentTypeRepository.findAll()
                .stream()
                .map(documentType -> DocumentTypeDto.builder()
                        .id(documentType.getId())
                        .value(documentType.getValue())
                        .build())
                .toList();
    }

    @Override
    public List<ContractKindDto> getAllContractKinds() {
        return contractKindRepository.findAll()
                .stream()
                .map(contractKind -> ContractKindDto.builder()
                        .id(contractKind.getId())
                        .value(contractKind.getValue())
                        .build())
                .toList();
    }
}
