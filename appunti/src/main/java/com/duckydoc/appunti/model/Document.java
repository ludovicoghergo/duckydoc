package com.duckydoc.appunti.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "format")
    private String format;

    @Column(name = "creationData")
    private int creationData;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @Column(name = "university")
    private String university;

    @Column(name = "year")
    private int year;

    @Column(name = "course")
    private String course;

    @Column (name = "fileUrl")
    private String fileUrl;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="userId")
    private User user;

    @OneToMany(mappedBy = "document")
    private List<Review> reviews;

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

    public Document() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getCreationData() {
        return creationData;
    }

    public void setCreationData(int creationData) {
        this.creationData = creationData;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
