package com.mydigitalbank.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "tb_client")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Evita problemas com lazy loading em equals/hashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long clientId;

    @NotBlank(message = "Full name cannot be blank.")
    @Size(max = 100, message = "Full name must not exceed 100 characters.")
    private String fullName;

    @NotBlank(message = "Document cannot be blank.")
    @Column(unique = true, nullable = false, length = 20)
    @Size(min = 11, max = 14, message = "Document must be between 11 (CPF) and 14 (CNPJ) characters.")
    private String document; // CPF ou CNPJ

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = true)
    private FinancialProfile financialProfile;

    // Método auxiliar para associação bidirecional
    public void setFinancialProfile(FinancialProfile financialProfile) {
        if (financialProfile == null) {
            if (this.financialProfile != null) {
                this.financialProfile.setClient(null);
            }
        } else {
            financialProfile.setClient(this);
        }
        this.financialProfile = financialProfile;
    }
}
