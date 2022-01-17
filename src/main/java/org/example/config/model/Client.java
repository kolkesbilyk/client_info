package org.example.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Long id;
    private String created;
    private String deleted;
    private Info info;
    private Telephones telephones;
}
