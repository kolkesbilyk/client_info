package org.example.config.service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.config.dao.DbRepository;
import org.example.config.model.Client;

@Named
public class ClientServiceImpl implements ClientService{
    @Inject
    private DbRepository repository;

    @Override
    public Client save(Client client) {
        repository.insertClient(client);
        return client;
    }

    @Override
    public Client update(Long id, Client client) {
        return repository.updateClient(id, client);
    }

    @Override
    public Client getClientById(Long id){
        return repository.getClientById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteClient(id);
    }

    @Override
    public boolean isActive(Long id){
        return repository.isActive(id);
    }
}
