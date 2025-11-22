package com.andres.springcloud.msvc.users.repositories;

import com.andres.springcloud.msvc.users.entities.Company;
import com.andres.springcloud.msvc.users.repositories.dao.CompanyRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
    Optional<Company> findByCompanyRuc(String companyRuc);
    Boolean existsCompanyByCompanyRuc(String companyRuc);
    //find all pageable and by state
    Page<Company> findAllByState(Boolean state, Pageable pageable);
}
