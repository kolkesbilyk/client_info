package org.example.config.dao;

import jakarta.inject.Named;
import org.example.config.model.Client;
import org.example.config.model.Info;
import org.example.config.model.Telephones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
public class DbRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/clients_db_info";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public Client insertClient(Client client){
        String insertSql = "INSERT INTO clients(create, delete) values(?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(insertSql);
            ps.setString(1, client.getCreated());
            ps.setString(2, client.getDeleted());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> getClients(){
        List<Client> data = new ArrayList<>();
        String select = "Select * from clients";
        try {
            PreparedStatement ps = getConnection().prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("client_id"));
                client.setCreated(rs.getString("created"));
                client.setDeleted(rs.getString("deleted"));
                data.add(client);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public Client getClientById(Long id) {
        Client client = new Client();
        String select = "select i.first_name, i.last_name, i.passport, i.birthday, t.telephone \n" +
                "from clients c\n" +
                "join info i on c.client_id = i.client_id\n" +
                "join telephones t on c.client_id = t.client_id\n" +
                "where c.client_id = (?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(select);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Telephones telephones = new Telephones();
                Info info = new Info();
                telephones.setTelephone(rs.getString("telephone"));
                info.setFirstName(rs.getString("first_name"));
                info.setLastname(rs.getString("last_name"));
                info.setPassport(rs.getString("passport"));
                info.setBirthday(rs.getString("birthday"));
                client.setInfo(info);
                client.setTelephones(telephones);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return client;
    }

    public Client updateClient(Long id, Client client){
        String updateSql = "UPDATE clients set created = (?), deleted = (?) where client_id = (?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(updateSql);
            ps.setString(1, client.getCreated());
            ps.setString(2, client.getDeleted());
            ps.setLong(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public void deleteClient(Long id){
        String deleteSql1 = "delete from telephones where client_id = (?)";
        String deleteSql2 = "delete from info where client_id = (?)";
        String deleteSql3 = "delete from clients where client_id = (?)";
        try {
            PreparedStatement ps1 = getConnection().prepareStatement(deleteSql1);
            ps1.setLong(1, id);
            ps1.executeUpdate();
            PreparedStatement ps2 = getConnection().prepareStatement(deleteSql2);
            ps2.setLong(1, id);
            ps2.executeUpdate();
            PreparedStatement ps3 = getConnection().prepareStatement(deleteSql3);
            ps3.setLong(1, id);
            ps3.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
