package com.example.duckydoc.DAO;

public class Document {
    private long id;
    private String title;
    private String nameFile;
    private String format;
    private Long size;
    private int creationData;
    private byte[] data;
    private int price;
    private String description;
    private String university;
    private int year;
    private String course;
    private User user;

    public Document(String title, String nameFile, String format, Long size, int creationData, int price, String description, String university, int year, String course, User user) {
        this.title = title;
        this.nameFile = nameFile;
        this.format = format;
        this.size = size;
        this.creationData = creationData;
        this.price = price;
        this.description = description;
        this.university = university;
        this.year = year;
        this.course = course;
        this.user = user;
    }

    public Document(String title, String nameFile, String format, int creationData, int price, String description, String university, int year, String course, User user) {
        this.title = title;
        this.nameFile = nameFile;
        this.format = format;
        this.creationData = creationData;
        this.price = price;
        this.description = description;
        this.university = university;
        this.year = year;
        this.course = course;
        this.user = user;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    public String getNameFile() {
        return nameFile;
    }

    public Long getSize() {
        return size;
    }

    public User getUser() {
        return user;
    }
}
