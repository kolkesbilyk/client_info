package org.example.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Telephones {
    private Long id;
    private Long clientId;
    private String telephone;
    private int typeId;
    private String created;
    private String deleted;
}
