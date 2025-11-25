package com.andres.springcloud.msvc.users.dto.response;

import com.andres.springcloud.msvc.users.dto.CompanyDto;
import com.andres.springcloud.msvc.users.dto.RoleDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUser {
    private Long id;
    private String username;
    private Boolean enabled;
    private boolean admin;
    private String email;
    private List<Long> roleIds;
    private List<RoleDto> roles;

}
