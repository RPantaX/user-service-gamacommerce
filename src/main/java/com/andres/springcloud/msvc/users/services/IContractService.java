package com.andres.springcloud.msvc.users.services;

import com.andres.springcloud.msvc.users.dto.ContractDto;
import com.andres.springcloud.msvc.users.dto.request.ContractRequest;

public interface IContractService {
    ContractDto createContract(ContractRequest contractRequest);
    ContractDto getContractById(Long contractId);
    ContractDto updateContract(Long contractId, ContractDto contractDto);
    void deleteContract(Long contractId);
}
