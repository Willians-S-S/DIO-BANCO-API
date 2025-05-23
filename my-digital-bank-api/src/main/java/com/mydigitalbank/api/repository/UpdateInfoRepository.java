package com.mydigitalbank.api.repository;

import com.mydigitalbank.api.domain.model.UpdateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateInfoRepository extends JpaRepository<UpdateInfo, Long> {
    // Métodos de consulta customizados podem ser adicionados aqui se necessário
}
