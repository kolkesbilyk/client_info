package org.example.config.dao;

import jakarta.inject.Named;
import org.example.config.model.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Named
public class DbRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private final LocalDate today = LocalDate.now();

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

    public void createTables(){
        String sqlTable1 = "create table if not exists clients(client_id serial primary key, created date, deleted date)";
        String sqlTable2 = "create table if not exists info(id serial primary key, client_id bigint, first_name varchar(15), last_name varchar(15), passport varchar(10), birthday date, created date, deleted date)";
        String sqlTable3 = "create table if not exists telephone(id serial primary key, client_id bigint, number varchar(11), type_id smallint, created date, deleted date, foreign key (client_id) references clients)";
        try(Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(sqlTable1);
            statement.executeUpdate(sqlTable2);
            statement.executeUpdate(sqlTable3);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Client insertClient(Client client){
        Byte one = 1;
        String insertSql1 = "insert into clients(created) values (date (?))";
        String insertSql2 = "insert into info(client_id, first_name, last_name, created) values ((?), (?), (?), date (?))";
        String insertSql3 = "insert into telephone(client_id, number, type_id, created) values ((?), (?), (?), date (?))";
        try (PreparedStatement ps1 = getConnection().prepareStatement(insertSql1);
        PreparedStatement ps2 = getConnection().prepareStatement(insertSql2);
        PreparedStatement ps3 = getConnection().prepareStatement(insertSql3)){
            ps1.setString(1, String.valueOf(today));
            ps1.executeUpdate();

            ps2.setLong(1, getLastId());
            ps2.setString(2, client.getFirstname());
            ps2.setString(3, client.getLastName());
            ps2.setString(4, String.valueOf(today));
            ps2.executeUpdate();

            ps3.setLong(1, getLastId());
            ps3.setString(2, client.getTelephones().get(one));
            ps3.setByte(3, (byte) 1);
            ps3.setString(4, String.valueOf(today));
            ps3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public Client getClientById(Long id) {
        Client client = new Client();
        Map<Byte,String> telephones = new HashMap<>();
        String select = "select c.client_id,first_name,last_name,passport,birthday,type_id,number from clients c join info i on c.client_id = i.client_id join telephone t on c.client_id = t.client_id where c.client_id = (?)";
        try (PreparedStatement ps = getConnection().prepareStatement(select)){
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                client.setFirstname(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setPassport(rs.getString("passport"));
                client.setBirthday(rs.getString("birthday"));
                telephones.put(rs.getByte("type_id"), rs.getString("number"));
                client.setTelephones(telephones);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return client;
    }

    public Client updateClient(Long id, Client client){
        Byte two = 2;
        String updateSql1 = "update info set birthday = date(?), passport = (?) where client_id = (?)";
        String updateSql2 = "insert into telephone(client_id, number, type_id, created) values ((?), (?), 2, date (?))";
        try (PreparedStatement ps1 = getConnection().prepareStatement(updateSql1);
        PreparedStatement ps2 = getConnection().prepareStatement(updateSql2)){

            ps1.setString(1, String.valueOf(client.getBirthday()));
            ps1.setString(2, client.getPassport());
            ps1.setLong(3, id);
            ps1.executeUpdate();

            ps2.setLong(1, id);
            ps2.setString(2, client.getTelephones().get(two));
            ps2.setString(3, String.valueOf(today));
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public void deleteClient(Long id){
        String deleteSql1 = "update clients set deleted = date(?) where client_id = (?)";
        String deleteSql2 = "update clients set deleted = date(?) where client_id = (?)";
        String deleteSql3 = "update clients set deleted = date(?) where client_id = (?)";
        try (PreparedStatement ps1 = getConnection().prepareStatement(deleteSql1);
            PreparedStatement ps2 = getConnection().prepareStatement(deleteSql2);
            PreparedStatement ps3 = getConnection().prepareStatement(deleteSql3)){

            ps1.setString(1, String.valueOf(today));
            ps1.setLong(2, id);
            ps1.executeUpdate();

            ps2.setString(1, String.valueOf(today));
            ps2.setLong(2, id);
            ps2.executeUpdate();

            ps3.setString(1, String.valueOf(today));
            ps3.setLong(2, id);
            ps3.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Long getLastId(){
        Long lastId = null;
        String sql = "select client_id from clients order by client_id desc limit 1";
        try(Statement statement = getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                lastId = rs.getLong(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lastId;
    }

    public boolean isActive(Long id){
        String date;
        boolean isActive = true;
        String sql = "select deleted from clients where client_id = (?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)){
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                date = rs.getString("deleted");
                if (date != null) isActive = false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return isActive;
    }
}
