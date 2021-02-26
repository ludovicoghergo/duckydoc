package com.DuckyDoc.Report.model;

import javax.persistence.*;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "documentId")
    private long documentId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    public Report(long documentId, String description, String status) {
        this.documentId = documentId;
        this.description = description;
        this.status = status;
    }

    public Report() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
