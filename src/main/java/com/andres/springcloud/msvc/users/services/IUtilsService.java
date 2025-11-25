package com.andres.springcloud.msvc.users.services;

import com.andres.springcloud.msvc.users.dto.CompanyTypeDto;
import com.andres.springcloud.msvc.users.dto.ContractKindDto;
import com.andres.springcloud.msvc.users.dto.DocumentTypeDto;
import com.andres.springcloud.msvc.users.entities.CompanyType;
import com.andres.springcloud.msvc.users.entities.ContractKind;

import java.util.List;

public interface IUtilsService {
    List<CompanyTypeDto> getAllCompanyTypes();
    List<DocumentTypeDto> getAllDocumentTypes();
    List<ContractKindDto> getAllContractKinds();
}
