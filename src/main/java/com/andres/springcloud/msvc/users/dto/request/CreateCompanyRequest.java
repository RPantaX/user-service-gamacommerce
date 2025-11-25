package com.andres.springcloud.msvc.users.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCompanyRequest {
    @NotNull
    private PersonRequest person;

    @NotNull
    private CompanyRequest company;
}
