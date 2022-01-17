package org.example.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private Long id;
    private Long clientId;
    private String firstName;
    private String lastname;
    private String passport;
    private String birthday;
    private String created;
    private String deleted;
}
