package com.andres.springcloud.msvc.users.dto.response;

import com.andres.springcloud.msvc.users.dto.AddressDto;
import com.andres.springcloud.msvc.users.dto.ContractDto;
import com.andres.springcloud.msvc.users.dto.PersonDto;
import com.andres.springcloud.msvc.users.dto.UserDto;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCompany {
    private Long id;

    private String companyRuc;

    private String companyName;

    private String companyTradeName;

    private String companyPhone;

    private String companyEmail;

    private String documentTypeName;

    private String companyDocumentNumber;

    private String companyTypeName;
    private AddressDto companyAddressDto;

    private PersonDto personDto;

    private ContractDto contractDto;

    private String image;
    private List<UserDto> responseUsers;
}
