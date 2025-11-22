package com.andres.springcloud.msvc.users.dto.response;

import com.andres.springcloud.msvc.users.dto.CompanyDto;
import com.andres.springcloud.msvc.users.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCompanyResponse {
    private CompanyDto companyDto;
    private UserDto userDto;
}
