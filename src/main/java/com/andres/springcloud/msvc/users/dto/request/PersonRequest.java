package com.andres.springcloud.msvc.users.dto.request;

import jakarta.validation.constraints.Email;
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
public class PersonRequest {
    @NotBlank
    private String personDocumentNumber;

    @NotBlank
    private String personName;

    @NotBlank
    private String personLastName;

    @NotBlank
    private String personPhoneNumber;

    @Email
    @NotBlank
    private String personEmailAddress;

    @NotBlank
    private String personAddressStreet;

    @NotBlank
    private String personAddressCity;

    @NotBlank
    private String personAddressState;

    @NotBlank
    private String personAddressPostalCode;

    @NotBlank
    private String personAddressCountry;

    @NotNull
    private Long personDocumentId;
}
