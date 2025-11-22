package com.andres.springcloud.msvc.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractRequest {
    private Integer contractTimeMonth;
    private Long contractKindId;
}
