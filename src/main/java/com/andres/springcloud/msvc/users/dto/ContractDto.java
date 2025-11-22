package com.andres.springcloud.msvc.users.dto;

import com.andres.springcloud.msvc.users.entities.ContractKind;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDto {
    private Long id;

    @NotNull
    @Positive
    private Integer timeMonth;
    private String document;

    @NotBlank
    private boolean contractState;

    private ContractKindDto contractKindDto;
    private boolean isActive;

    public boolean isActive() {
        return contractState;
    }

}