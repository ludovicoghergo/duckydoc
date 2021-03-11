package com.example.duckydoc.DAO;

public class Report {
    private long id;
    private long documentId;
    private String description;
    private String status;
    private Account user;

    public Report(long documentId, String description, String status, Account user) {
        this.documentId = documentId;
        this.description = description;
        this.status = status;
        this.user = user;
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

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
}
