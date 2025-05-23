package com.mydigitalbank.api.service.impl;

import com.mydigitalbank.api.domain.model.ServiceItem;
import com.mydigitalbank.api.repository.ServiceItemRepository;
import com.mydigitalbank.api.service.ServiceItemService;
import com.mydigitalbank.api.domain.exception.NotFoundException;
import com.mydigitalbank.api.domain.exception.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {

    private final ServiceItemRepository serviceItemRepository;

    public ServiceItemServiceImpl(ServiceItemRepository serviceItemRepository) {
        this.serviceItemRepository = serviceItemRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceItem> findAll() {
        return serviceItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ServiceItem findById(Long id) {
        return serviceItemRepository.findById(id).orElseThrow(() -> new NotFoundException("ServiceItem", id));
    }

    @Transactional
    @Override
    public ServiceItem create(ServiceItem itemToCreate) {
        if (itemToCreate.getImageUrl() == null || itemToCreate.getImageUrl().isBlank()) {
            throw new BusinessException("ServiceItem image URL cannot be empty.");
        }
        if (itemToCreate.getServiceDescription() == null || itemToCreate.getServiceDescription().isBlank()) {
            throw new BusinessException("ServiceItem description cannot be empty.");
        }
        // Poderia haver validações de unicidade aqui se necessário
        return serviceItemRepository.save(itemToCreate);
    }

    @Transactional
    @Override
    public ServiceItem update(Long id, ServiceItem itemToUpdate) {
        ServiceItem dbItem = this.findById(id);

        if (itemToUpdate.getImageUrl() != null) {
            dbItem.setImageUrl(itemToUpdate.getImageUrl());
        }
        if (itemToUpdate.getServiceDescription() != null) {
            dbItem.setServiceDescription(itemToUpdate.getServiceDescription());
        }
        return serviceItemRepository.save(dbItem);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        ServiceItem item = this.findById(id);
        serviceItemRepository.delete(item);
    }
}
