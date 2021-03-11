package com.DuckyDoc.Gateway.report.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Utente {
    private long id;
    private String nome;
    private String email;
    private String password;
    private boolean isModeratore;
    private List<Report> reports;
}
