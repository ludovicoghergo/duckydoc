package com.DuckyDoc.Gateway.utenti.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Report {
    private long id;
    private long documentId;
    private String description;
    private String status;
    private Utente user;
}
