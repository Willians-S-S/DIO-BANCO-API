package com.mydigitalbank.api.repository;

import com.mydigitalbank.api.domain.model.FinancialProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FinancialProfileRepository extends JpaRepository<FinancialProfile, Long> {
    boolean existsByAccountNumber(String accountNumber);
    Optional<FinancialProfile> findByAccountNumber(String accountNumber);
}
