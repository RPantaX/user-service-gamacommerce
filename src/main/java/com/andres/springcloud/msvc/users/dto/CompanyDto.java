package com.andres.springcloud.msvc.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {
    private Long id;

    private String companyRuc;

    private String companyName;

    private String companyTradeName;

    private String companyPhone;

    private String companyEmail;

    private DocumentTypeDto documentType;

    private CompanyTypeDto companyType;

    private PersonDto person;

    private AddressDto addressDto;

    private ContractDto contract;

    private String image;
}
