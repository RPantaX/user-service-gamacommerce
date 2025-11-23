package com.andres.springcloud.msvc.users.controllers;

import com.andres.springcloud.msvc.users.dto.request.CreateCompanyRequest;
import com.andres.springcloud.msvc.users.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.Constants;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.util.ApiResponse;

@RestController
@RequestMapping("/v1/user-service/company")
@RequiredArgsConstructor
public class CompanyController {
    private final ICompanyService companyService;

    @GetMapping("/list/pageable")
    public ResponseEntity<ApiResponse> listAllCompanies(
            @RequestParam(value = "pageNo", defaultValue = Constants.NUM_PAG_BY_DEFECT, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.SIZE_PAG_BY_DEFECT, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.ORDER_BY_DEFECT_ALL, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.ORDER_DIRECT_BY_DEFECT, required = false) String sortDir,
            @RequestParam(value = "state", defaultValue = "true", required = false) boolean state
    ) {
        return ResponseEntity.ok(ApiResponse.ok("List of all Companies", companyService.getAllCompaniesPageable(
                pageNo, pageSize, sortBy, sortDir, state
        )));
    }
    @GetMapping("/findByRUC")
    public ResponseEntity<ApiResponse> findCompanyByRUC(@RequestParam("RUC") String ruc) {
        return ResponseEntity.ok(ApiResponse.ok("Company found by RUC", companyService.getCompanyByRuc(ruc)));
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCompany(@RequestBody CreateCompanyRequest companyRequest) {
        return ResponseEntity.ok(ApiResponse.ok("Company created", companyService.createCompany(companyRequest)));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Company deleted", companyService.deleteCompany(id)));
    }
}
