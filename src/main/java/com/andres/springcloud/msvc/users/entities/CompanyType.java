package com.andres.springcloud.msvc.users.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_type_id")
    private Long id;

    @Column(name = "company_type_value", nullable = false, unique = true)
    private String value;
}
