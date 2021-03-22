package com.DuckyDoc.Gateway.report.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Utente {
    private long id;
    private String idGoogle;
    private String name;
    private String email;
    private String surname;
    private boolean isMod;
    private List<Report> reports;
}
