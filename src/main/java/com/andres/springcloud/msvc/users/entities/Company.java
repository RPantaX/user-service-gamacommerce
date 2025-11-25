package com.andres.springcloud.msvc.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    @Column(name = "company_ruc", nullable = false, unique = true, length = 11)
    private String companyRuc;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @Column(name = "company_trade_name")
    private String companyTradeName;
    @Column(name = "company_phone")
    private String companyPhone;
    @Column(name = "company_email")
    private String companyEmail;

    @ManyToOne
    @JoinColumn(name = "company_type_id", nullable = false)
    private CompanyType companyType;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private DocumentType documentType;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address companyAddress;
    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(name = "company_image")
    private String image;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "modified_by_user", nullable = false, length = 15)
    private String modifiedByUser;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "state", nullable = false)
    private Boolean state;

}
