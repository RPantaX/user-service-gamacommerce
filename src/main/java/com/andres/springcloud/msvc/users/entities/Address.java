package com.andres.springcloud.msvc.users.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @NotBlank
    @Column(name = "address_street")
    private String street;

    @NotBlank
    @Column(name = "address_city")
    private String city;

    @NotBlank
    @Column(name = "address_state")
    private String state;

    @NotBlank
    @Column(name = "address_postal_code")
    private String postalCode;

    @NotBlank
    @Column(name = "address_country")
    private String country;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "modified_by_user", nullable = false, length = 15)
    private String modifiedByUser;

}
