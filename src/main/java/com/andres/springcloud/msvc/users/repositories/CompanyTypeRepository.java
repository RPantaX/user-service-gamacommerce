package com.andres.springcloud.msvc.users.repositories;

import com.andres.springcloud.msvc.users.entities.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
}
