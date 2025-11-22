package com.andres.springcloud.msvc.users.services;

import com.andres.springcloud.msvc.users.dto.CompanyDto;
import com.andres.springcloud.msvc.users.dto.request.CreateCompanyRequest;
import com.andres.springcloud.msvc.users.dto.request.ResponseListPageableCompany;
import com.andres.springcloud.msvc.users.dto.response.CreateCompanyResponse;
import com.andres.springcloud.msvc.users.dto.response.ResponseCompany;

public interface ICompanyService {
    CreateCompanyResponse createCompany(CreateCompanyRequest companyRequest);
    ResponseListPageableCompany getAllCompaniesPageable(int pageNumber, int pageSize, String orderBy, String sortDir, boolean state);
    ResponseCompany getCompanyByRuc(String ruc);
    CompanyDto updateCompany(Long companyId, CreateCompanyRequest companyRequest);
    CompanyDto deleteCompany(Long companyId);
    Boolean existByRuc(String ruc);

}
