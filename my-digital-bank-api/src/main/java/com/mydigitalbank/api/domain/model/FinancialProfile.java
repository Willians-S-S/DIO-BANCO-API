package com.mydigitalbank.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_financial_profile")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FinancialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long profileId;

    @NotBlank(message = "Account number cannot be blank.")
    @Column(unique = true, nullable = false, length = 20)
    @Size(max = 20, message = "Account number must not exceed 20 characters.")
    private String accountNumber;

    @NotBlank(message = "Branch code cannot be blank.")
    @Column(nullable = false, length = 10)
    @Size(max = 10, message = "Branch code must not exceed 10 characters.")
    private String branchCode;

    @NotNull(message = "Current balance cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Current balance must be non-negative.")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @NotNull(message = "Credit limit cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Credit limit must be non-negative.")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal creditLimit = BigDecimal.ZERO;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_profile_investments", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "investment_description", length = 255)
    private List<String> investments = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false) // Chave estrangeira para Client
    private Client client;
}
