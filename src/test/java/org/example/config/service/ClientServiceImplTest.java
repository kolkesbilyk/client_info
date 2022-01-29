package org.example.config.service;

import org.example.config.dao.DbRepository;
import org.example.config.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplTest {
    private DbRepository repository = new DbRepository();
    private Long id = 2L;
    @Test
    public void insertTest(){
        Map<Byte,String> telephones = new HashMap<>();
        telephones.put((byte) 1, "0993859767");
        Client client = new Client("Mykola", "Bilyk", telephones);
        repository.insertClient(client);
    }

    @Test
    public void selectByIdTest(){
        Client clientById = repository.getClientById(id);
        System.out.println(clientById);
    }

    @Test
    public void updateTest(){
        Map<Byte,String> telephone = new HashMap<>();
        telephone.put((byte)2, "0502211920");
        Client client = new Client();
        client.setPassport("KO55555");
        client.setBirthday("1994-01-30");
        client.setTelephones(telephone);
        repository.updateClient(id, client);
    }

    @Test
    public void createTable(){
        repository.createTables();
    }

    @Test
    public void activatedTest(){
        System.out.println(repository.isActive(2L));
    }
}