package com.mydigitalbank.api.service.impl;

import com.mydigitalbank.api.domain.model.UpdateInfo;
import com.mydigitalbank.api.repository.UpdateInfoRepository;
import com.mydigitalbank.api.service.UpdateInfoService;
import com.mydigitalbank.api.domain.exception.NotFoundException;
import com.mydigitalbank.api.domain.exception.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UpdateInfoServiceImpl implements UpdateInfoService {

    private final UpdateInfoRepository updateInfoRepository;

    public UpdateInfoServiceImpl(UpdateInfoRepository updateInfoRepository) {
        this.updateInfoRepository = updateInfoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UpdateInfo> findAll() {
        return updateInfoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public UpdateInfo findById(Long id) {
        return updateInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("UpdateInfo", id));
    }

    @Transactional
    @Override
    public UpdateInfo create(UpdateInfo infoToCreate) {
        if (infoToCreate.getImageUrl() == null || infoToCreate.getImageUrl().isBlank()) {
            throw new BusinessException("UpdateInfo image URL cannot be empty.");
        }
        if (infoToCreate.getUpdateDescription() == null || infoToCreate.getUpdateDescription().isBlank()) {
            throw new BusinessException("UpdateInfo description cannot be empty.");
        }
        return updateInfoRepository.save(infoToCreate);
    }

    @Transactional
    @Override
    public UpdateInfo update(Long id, UpdateInfo infoToUpdate) {
        UpdateInfo dbInfo = this.findById(id);

        if (infoToUpdate.getImageUrl() != null) {
            dbInfo.setImageUrl(infoToUpdate.getImageUrl());
        }
        if (infoToUpdate.getUpdateDescription() != null) {
            dbInfo.setUpdateDescription(infoToUpdate.getUpdateDescription());
        }
        return updateInfoRepository.save(dbInfo);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        UpdateInfo info = this.findById(id);
        updateInfoRepository.delete(info);
    }
}
