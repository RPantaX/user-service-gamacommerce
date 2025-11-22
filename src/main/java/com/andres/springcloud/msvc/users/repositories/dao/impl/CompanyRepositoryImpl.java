package com.andres.springcloud.msvc.users.repositories.dao.impl;

import com.andres.springcloud.msvc.users.entities.Company;
import com.andres.springcloud.msvc.users.repositories.dao.CompanyRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Company> findByCompanyRucWithAllrelations(String companyRuc) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> companyRoot = query.from(Company.class);

        // Fetch relationships
        companyRoot.fetch("companyType", JoinType.LEFT);
        companyRoot.fetch("companyAddress", JoinType.LEFT);
        companyRoot.fetch("documentType", JoinType.LEFT);

        Fetch<Company, ?> contractFetch = companyRoot.fetch("contract", JoinType.LEFT);
        contractFetch.fetch("contractKind", JoinType.LEFT);

        Fetch<Company, ?> personFetch = companyRoot.fetch("person", JoinType.LEFT);
        personFetch.fetch("address", JoinType.LEFT);
        personFetch.fetch("documentType", JoinType.LEFT);

        // Add where clause
        query.select(companyRoot).where(cb.equal(companyRoot.get("companyRuc"), companyRuc));

        // Execute query
        return entityManager.createQuery(query).getResultStream().findFirst();
    }

    @Override
    public Optional<Company> findByIdWithAllRelations(String id) {
        return Optional.empty();
    }
}
