package com.mydigitalbank.api.service;

import com.mydigitalbank.api.domain.model.Client;
import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client findById(Long id);
    Client findByDocument(String document);
    Client create(Client clientToCreate);
    Client update(Long id, Client clientToUpdate);
    void delete(Long id);
}
