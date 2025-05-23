package com.mydigitalbank.api.repository;

import com.mydigitalbank.api.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByDocument(String document);
    Optional<Client> findByDocument(String document);
}
