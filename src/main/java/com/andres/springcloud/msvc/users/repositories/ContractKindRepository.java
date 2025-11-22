package com.andres.springcloud.msvc.users.repositories;


import com.andres.springcloud.msvc.users.entities.ContractKind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractKindRepository extends JpaRepository<ContractKind, Long> {
}
