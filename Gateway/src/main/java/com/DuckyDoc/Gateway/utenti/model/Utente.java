package com.DuckyDoc.Gateway.utenti.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class Utente {
    private long id;
    private String idGoogle;
    private String name;
    private String email;
    private String surname;
    private int credits;
    private boolean isMod;
    private List<Report> reports;
}
