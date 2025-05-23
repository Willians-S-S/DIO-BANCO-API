package com.mydigitalbank.api.service;

import com.mydigitalbank.api.domain.model.ServiceItem;
import java.util.List;

public interface ServiceItemService {
    List<ServiceItem> findAll();
    ServiceItem findById(Long id);
    ServiceItem create(ServiceItem itemToCreate);
    ServiceItem update(Long id, ServiceItem itemToUpdate);
    void delete(Long id);
}
