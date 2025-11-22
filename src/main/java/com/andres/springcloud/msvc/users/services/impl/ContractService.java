package com.andres.springcloud.msvc.users.services.impl;

import com.andres.springcloud.msvc.users.dto.ContractDto;
import com.andres.springcloud.msvc.users.dto.request.ContractRequest;
import com.andres.springcloud.msvc.users.entities.Contract;
import com.andres.springcloud.msvc.users.entities.ContractKind;
import com.andres.springcloud.msvc.users.repositories.ContractRepository;
import com.andres.springcloud.msvc.users.services.IContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.Constants;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractService implements IContractService {
    private final ContractRepository contractRepository;
    @Override
    public ContractDto createContract(ContractRequest contractRequest) {
        Contract contractSaved = contractRepository.save(buildContract(contractRequest));
        return mapToContractDto(contractSaved);
    }

    @Override
    public ContractDto getContractById(Long contractId) {
        return null;
    }

    @Override
    public ContractDto updateContract(Long contractId, ContractDto contractDto) {
        return null;
    }

    @Override
    public void deleteContract(Long contractId) {

    }
    private Contract buildContract(ContractRequest contractRequest) {
        return Contract.builder()
                .contractKind(ContractKind.builder().id(contractRequest.getContractKindId()).build())
                .contractState(Constants.STATUS_ACTIVE)
                .timeMonth(contractRequest.getContractTimeMonth())
                .modifiedByUser(!com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession().isEmpty() ? com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession() : "SYSTEM")
                .createdAt(Constants.getTimestamp())
                .build();
    }
    public static ContractDto mapToContractDto(Contract contract) {
        return ContractDto.builder()
                .id(contract.getId())
                .contractState(contract.getContractState())
                .timeMonth(contract.getTimeMonth())
                .build();
    }
}
