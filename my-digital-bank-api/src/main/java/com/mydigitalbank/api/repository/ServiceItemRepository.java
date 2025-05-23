package com.mydigitalbank.api.repository;

import com.mydigitalbank.api.domain.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    // Métodos de consulta customizados podem ser adicionados aqui se necessário
}
