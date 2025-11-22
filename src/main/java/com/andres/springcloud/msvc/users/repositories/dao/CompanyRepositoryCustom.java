package com.andres.springcloud.msvc.users.repositories.dao;

import com.andres.springcloud.msvc.users.entities.Company;

import java.util.Optional;

public interface CompanyRepositoryCustom {
    Optional<Company> findByCompanyRucWithAllrelations(String companyRuc);
    Optional<Company> findByIdWithAllRelations(String id);
}
