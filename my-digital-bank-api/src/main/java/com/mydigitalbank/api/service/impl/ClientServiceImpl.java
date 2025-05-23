package com.mydigitalbank.api.service.impl;

import com.mydigitalbank.api.domain.model.Client;
import com.mydigitalbank.api.domain.model.FinancialProfile;
import com.mydigitalbank.api.repository.ClientRepository;
import com.mydigitalbank.api.repository.FinancialProfileRepository;
import com.mydigitalbank.api.service.ClientService;
import com.mydigitalbank.api.domain.exception.NotFoundException;
import com.mydigitalbank.api.domain.exception.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final FinancialProfileRepository financialProfileRepository;

    public ClientServiceImpl(ClientRepository clientRepository, FinancialProfileRepository financialProfileRepository) {
        this.clientRepository = clientRepository;
        this.financialProfileRepository = financialProfileRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client", id));
    }

    @Transactional(readOnly = true)
    @Override
    public Client findByDocument(String document) {
        return clientRepository.findByDocument(document).orElseThrow(() -> new NotFoundException("Client not found with document: " + document));
    }

    @Transactional
    @Override
    public Client create(Client clientToCreate) {
        if (clientToCreate.getDocument() == null || clientToCreate.getDocument().isBlank()) {
            throw new BusinessException("Client document cannot be null or empty.");
        }
        if (clientRepository.existsByDocument(clientToCreate.getDocument())) {
            throw new BusinessException("A client with document '" + clientToCreate.getDocument() + "' already exists.");
        }

        FinancialProfile profileToCreate = clientToCreate.getFinancialProfile();
        if (profileToCreate != null) {
            if (profileToCreate.getAccountNumber() == null || profileToCreate.getAccountNumber().isBlank()) {
                 throw new BusinessException("Financial profile account number cannot be null or empty.");
            }
            if (financialProfileRepository.existsByAccountNumber(profileToCreate.getAccountNumber())) {
                throw new BusinessException("A financial profile with account number '" + profileToCreate.getAccountNumber() + "' already exists.");
            }
            // Garante a associação bidirecional
            clientToCreate.setFinancialProfile(profileToCreate); // Isso também chama profileToCreate.setClient(clientToCreate)
        }
        
        return clientRepository.save(clientToCreate);
    }

    @Transactional
    @Override
    public Client update(Long id, Client clientToUpdate) {
        Client dbClient = this.findById(id); // Garante que o cliente existe

        if (clientToUpdate.getDocument() != null && !clientToUpdate.getDocument().equals(dbClient.getDocument())) {
            if (clientRepository.existsByDocument(clientToUpdate.getDocument())) {
                throw new BusinessException("Another client with document '" + clientToUpdate.getDocument() + "' already exists.");
            }
            dbClient.setDocument(clientToUpdate.getDocument());
        }
        
        if (clientToUpdate.getFullName() != null) {
            dbClient.setFullName(clientToUpdate.getFullName());
        }

        FinancialProfile profileChanges = clientToUpdate.getFinancialProfile();
        FinancialProfile dbProfile = dbClient.getFinancialProfile();

        if (profileChanges != null) {
            if (dbProfile == null) { // Se não existia perfil, cria um novo
                if (profileChanges.getAccountNumber() == null || profileChanges.getAccountNumber().isBlank()) {
                    throw new BusinessException("Financial profile account number cannot be null or empty for a new profile.");
                }
                if (financialProfileRepository.existsByAccountNumber(profileChanges.getAccountNumber())) {
                    throw new BusinessException("A financial profile with account number '" + profileChanges.getAccountNumber() + "' already exists.");
                }
                dbProfile = new FinancialProfile();
                dbClient.setFinancialProfile(dbProfile); // Associa ao cliente
            }

            if (profileChanges.getAccountNumber() != null && !profileChanges.getAccountNumber().equals(dbProfile.getAccountNumber())) {
                if (financialProfileRepository.existsByAccountNumber(profileChanges.getAccountNumber())) {
                   throw new BusinessException("Another financial profile with account number '" + profileChanges.getAccountNumber() + "' already exists.");
                }
                dbProfile.setAccountNumber(profileChanges.getAccountNumber());
            }
            if (profileChanges.getBranchCode() != null) dbProfile.setBranchCode(profileChanges.getBranchCode());
            if (profileChanges.getCurrentBalance() != null) dbProfile.setCurrentBalance(profileChanges.getCurrentBalance());
            if (profileChanges.getCreditLimit() != null) dbProfile.setCreditLimit(profileChanges.getCreditLimit());
            if (profileChanges.getInvestments() != null) dbProfile.setInvestments(profileChanges.getInvestments());
        
        } else if (dbProfile != null) { // Se profileChanges é null mas dbProfile existe, remove o perfil
            dbClient.setFinancialProfile(null); // orphanRemoval=true cuidará da exclusão
        }
        
        return clientRepository.save(dbClient);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Client client = this.findById(id);
        clientRepository.delete(client); // Cascading deve remover FinancialProfile também
    }
}
