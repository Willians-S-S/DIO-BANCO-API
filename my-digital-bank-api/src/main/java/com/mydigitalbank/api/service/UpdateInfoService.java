package com.mydigitalbank.api.service;

import com.mydigitalbank.api.domain.model.UpdateInfo;
import java.util.List;

public interface UpdateInfoService {
    List<UpdateInfo> findAll();
    UpdateInfo findById(Long id);
    UpdateInfo create(UpdateInfo infoToCreate);
    UpdateInfo update(Long id, UpdateInfo infoToUpdate);
    void delete(Long id);
}
