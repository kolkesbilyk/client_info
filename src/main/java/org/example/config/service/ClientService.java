package org.example.config.service;

import org.example.config.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    Client save(Client client);
    Client update(Long id, Client client);
    List<Client> getClients();
    Client getClientById(Long id);
    void delete(Long id);
}
