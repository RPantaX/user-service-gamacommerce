package com.andres.springcloud.msvc.users.services.impl;

import com.andres.springcloud.msvc.users.dto.*;
import com.andres.springcloud.msvc.users.dto.constants.UsersErrorEnum;
import com.andres.springcloud.msvc.users.dto.request.CreateCompanyRequest;
import com.andres.springcloud.msvc.users.dto.request.ResponseListPageableCompany;
import com.andres.springcloud.msvc.users.dto.response.CreateCompanyResponse;
import com.andres.springcloud.msvc.users.dto.response.ResponseCompany;
import com.andres.springcloud.msvc.users.dto.response.ResponseSunat;
import com.andres.springcloud.msvc.users.entities.*;
import com.andres.springcloud.msvc.users.repositories.*;
import com.andres.springcloud.msvc.users.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.Constants;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.util.ValidateUtil;

import java.util.List;

import static com.andres.springcloud.msvc.users.services.impl.AddressService.mapToAddressDto;
import static com.andres.springcloud.msvc.users.services.impl.ContractService.mapToContractDto;
import static com.andres.springcloud.msvc.users.services.impl.PersonService.mapToPersonDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;
    //Just for references to other services
    private final PersonRepository personRepository;
    private final CompanyTypeRepository companyTypeRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ContractRepository contractRepository;
    private final AddressRepository addressRepository;
    private final IPersonService iPersonService;
    private final IContractService iContractService;
    private final IRestSunatService iRestSunatService;
    private final IAddressService iAddressService;
    private final IUserService iUserService;

    @Override
    @Transactional
    public CreateCompanyResponse createCompany(CreateCompanyRequest companyRequest) {
        log.info("Creating company with data: {}", companyRequest);
        validateCompanyData(companyRequest);
        PersonDto personDto = iPersonService.createPerson(companyRequest.getPerson());
        ContractDto contractDto = iContractService.createContract(companyRequest.getCompany().getContractRequest());
        Company companyToSave = buildCompanyToSave(companyRequest, personDto, contractDto);
        Company savedCompany = companyRepository.save(companyToSave);
        UserRequest user =  createUserForPersonCompany(personDto, savedCompany.getId() );
        UserDto userDto = com.andres.springcloud.msvc.users.dto.constants.Constants.mapToUserDto(iUserService.save(user));
        return CreateCompanyResponse.builder()
                .companyDto(mapToCompanyDto(savedCompany))
                .userDto(userDto)
                .build();
    }

    @Override
    public ResponseListPageableCompany getAllCompaniesPageable(int pageNumber, int pageSize, String orderBy, String sortDir, boolean state) {
        log.info("Retrieving companies - Page: {}, Size: {}, OrderBy: {}, SortDir: {}, State: {}",
                pageNumber, pageSize, orderBy, sortDir, state);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<CompanyDto> companiesPage = companyRepository.findAllByState(state, pageable)
                .map(this::mapToCompanyDto);
        return ResponseListPageableCompany.builder()
                .companyDtoList(companiesPage.getContent())
                .pageNumber(companiesPage.getNumber())
                .pageSize(companiesPage.getSize())
                .totalPages(companiesPage.getTotalPages())
                .totalElements(companiesPage.getTotalElements())
                .end(companiesPage.isLast())
                .build();
    }

    @Override
    public ResponseCompany getCompanyByRuc(String ruc) {
        log.info("Retrieving company with RUC: {}", ruc);
        Company company = companyRepository.findByCompanyRucWithAllrelations(ruc).orElse(null);
        ValidateUtil.requerido(company, UsersErrorEnum.COMPANY_NOT_FOUND_ERCO00011);
        List<User> users =iUserService.findAllByCompanyId(company.getId());
        return ResponseCompany.builder()
                .id(company.getId())
                .companyRuc(company.getCompanyRuc())
                .companyName(company.getCompanyName().toUpperCase())
                .companyTradeName(company.getCompanyTradeName().toUpperCase())
                .companyPhone(company.getCompanyPhone())
                .companyEmail(company.getCompanyEmail().toUpperCase())
                .companyTypeName(company.getCompanyType().getValue())
                .companyDocumentNumber(company.getDocumentType().getValue())
                .companyAddressDto(mapToAddressDto(company.getCompanyAddress()))
                .personDto(mapToPersonDto(company.getPerson()))
                .contractDto(mapToContractDto(company.getContract()))
                .image(company.getImage())
                .responseUsers(users.stream().map(com.andres.springcloud.msvc.users.dto.constants.Constants::mapToUserDto).toList())
                .build();
    }

    @Override
    public CompanyDto updateCompany(Long companyId, CreateCompanyRequest companyRequest) {
        return null;
    }

    @Override
    public CompanyDto deleteCompany(Long companyId) {
        log.info("Deleting company with ID: {}", companyId);
        Company company = companyRepository.findById(companyId).orElse(null);
        ValidateUtil.requerido(company, UsersErrorEnum.COMPANY_NOT_FOUND_ERCO00011);
        company.setState(Constants.STATUS_INACTIVE);
        return mapToCompanyDto(companyRepository.save(company));
    }

    @Override
    public Boolean existByRuc(String ruc) {
        return companyRepository.existsCompanyByCompanyRuc(ruc);
    }

    @Override
    public Company getCompanyReferenceById(Long companyId) {
        return companyRepository.getReferenceById(companyId);
    }

    @Override
    public Boolean existById(Long companyId) {
        return companyRepository.existsById(companyId);
    }

    private void validateCompanyData(CreateCompanyRequest companyRequest) {
        // Implement validation logic here
        log.info("Validating company data: {}", companyRequest);
        Company company =  companyRepository.findByCompanyRuc(companyRequest.getCompany().getCompanyRUC()).orElse(null);
        if (company != null) {
            log.error("Company ruc already exists", companyRequest.getCompany().getCompanyRUC());
            ValidateUtil.requerido(null, UsersErrorEnum.COMPANY_ALREADY_EXISTS_ERCO00012);
        }

    }
    private Company buildCompanyToSave(CreateCompanyRequest companyRequest, PersonDto personDto, ContractDto contractDto) {
        ResponseSunat responseSunat = iRestSunatService.getInfoSunat(companyRequest.getCompany().getCompanyRUC());
        AddressDto addressDto = iAddressService.createCompanyAddress(responseSunat);
        return Company.builder()
                .companyRuc(companyRequest.getCompany().getCompanyRUC())
                .companyName(responseSunat.getRazon_social().toUpperCase())
                .companyAddress(addressRepository.getReferenceById(addressDto.getId()))
                .companyTradeName(companyRequest.getCompany().getCompanyTradeName())
                .companyPhone(companyRequest.getCompany().getCompanyPhone())
                .companyEmail(companyRequest.getCompany().getCompanyEmail())
                .companyType(CompanyType.builder().id(companyRequest.getCompany().getCompanyTypeId()).build())
                .documentType(DocumentType.builder().id(companyRequest.getCompany().getCompanyDocumentId()).build())
                .person(personRepository.getReferenceById(personDto.getId()))
                .contract(contractRepository.getReferenceById(contractDto.getId()))
                .image(companyRequest.getCompany().getImage())
                .state(Constants.STATUS_ACTIVE)
                .createdAt(Constants.getTimestamp())
                .modifiedByUser(!com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession().isEmpty() ? com.andres.springcloud.msvc.users.dto.constants.Constants.getUserInSession() : "SYSTEM")
                .build();
    }
    private CompanyDto mapToCompanyDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .companyRuc(company.getCompanyRuc())
                .companyName(company.getCompanyName())
                .companyTradeName(company.getCompanyTradeName())
                .companyPhone(company.getCompanyPhone())
                .companyEmail(company.getCompanyEmail())
                .image(company.getImage())
                .build();
    }

    private UserRequest createUserForPersonCompany(PersonDto personDto, Long companyId) {
        log.info("Creating user for person with ID: {}", personDto.getId());
        String temporaryPassword = com.andres.springcloud.msvc.users.dto.constants.Constants.createDefaultPassword(personDto);
        return UserRequest.builder()
                .username(personDto.getDocumentNumber().toUpperCase())
                .document(personDto.getDocumentNumber().toUpperCase())
                .documentId(personDto.getDocumentTypeId())
                .password(temporaryPassword)
                .email(personDto.getEmailAddress().toUpperCase())
                .companyId(companyId)
                .admin(true)
                .build();
    }

}
