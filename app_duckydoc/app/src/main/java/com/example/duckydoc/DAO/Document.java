package com.example.duckydoc.DAO;

public class Document {
    private long id;
    private String title;
    private String format;
    private int creationData;
    private int price;
    private String description;
    private String university;
    private int year;
    private String course;
    private String fileUrl;
    private User user;

    public Document(long id, String title, String format, int creationData, int price, String description, String university, int year, String course, String fileUrl, User user) {
        this.id = id;
        this.title = title;
        this.format = format;
        this.creationData = creationData;
        this.price = price;
        this.description = description;
        this.university = university;
        this.year = year;
        this.course = course;
        this.fileUrl = fileUrl;
        this.user = user;
    }

    public Document(String title, String format, int creationData, int price, String description, String university, int year, String course, String fileUrl, User user) {
        this.title = title;
        this.format = format;
        this.creationData = creationData;
        this.price = price;
        this.description = description;
        this.university = university;
        this.year = year;
        this.course = course;
        this.fileUrl = fileUrl;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFormat() {
        return format;
    }

    public int getCreationData() {
        return creationData;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getUniversity() {
        return university;
    }

    public int getYear() {
        return year;
    }

    public String getCourse() {
        return course;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public User getUser() {
        return user;
    }
}
