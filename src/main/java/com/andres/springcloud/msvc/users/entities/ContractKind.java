package com.andres.springcloud.msvc.users.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contract_kind")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractKind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_kind_id")
    private Long id;

    @Column(name = "contract_kind_value", nullable = false, unique = true)
    private String value;
}
