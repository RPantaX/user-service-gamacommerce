package com.andres.springcloud.msvc.users.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {
    @NotNull
    private ContractRequest contractRequest;
    @NotNull
    private Long companyTypeId;
    private Long companyDocumentId;
    private String companyImage;
    private String image;
    @NotBlank
    private String companyTradeName;

    @NotBlank
    private String companyRUC;
    private String companyPhone;
    private String companyEmail;
}
