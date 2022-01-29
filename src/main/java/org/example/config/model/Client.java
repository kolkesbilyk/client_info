package org.example.config.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Client {
    private Long id;
    private String firstname;
    private String lastName;
    private String passport;
    private LocalDate birthday;
    private Map<Byte,String> telephones = new HashMap<>();
    private String created;
    private String deleted;

    public Client(String firstname, String lastName, Map<Byte, String> telephones) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.telephones = telephones;
    }

    public Client(String passport, LocalDate birthday, Map<Byte, String> telephones) {
        this.passport = passport;
        this.birthday = birthday;
        this.telephones = telephones;
    }

    public void setBirthday(String birthday) {
        if (birthday != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.birthday = LocalDate.parse(birthday, dtf);
        } else this.birthday = null;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstname='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passport='" + passport + '\'' +
                ", birthday=" + birthday +
                ", telephones=" + telephones +
                '}';
    }
}
