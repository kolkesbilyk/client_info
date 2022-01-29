package org.example.config.service;

import org.example.config.model.Client;

public interface ClientService {
    Client save(Client client);
    Client update(Long id, Client client);
    Client getClientById(Long id);
    void delete(Long id);
    boolean isActive(Long id);
}
