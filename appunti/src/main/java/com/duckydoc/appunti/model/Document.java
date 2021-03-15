package com.duckydoc.appunti.model;

import javax.persistence.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "nameFile")
    private String nameFile;

    @Column(name = "data")
    @Lob
    private byte[] data;

    @Column(name = "size")
    private Long size;

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

    @ManyToOne(cascade = { CascadeType.ALL })
    @PrimaryKeyJoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "document")
    private List<Review> reviews;

    public Document(String university, String course, String format, int year) {
        this.format = format;
        this.university = university;
        this.year = year;
        this.course = course;
    }

    public Document(String university, String course, String format) {
        this.format = format;
        this.university = university;
        this.course = course;
    }

    public Document(String title, int creationData, int price, String description, String university, int year,
            String course, User user) {
        this.title = title;
        this.creationData = creationData;
        this.price = price;
        this.description = description;
        this.university = university;
        this.year = year;
        this.course = course;
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

    public String getnameFile() {
        return nameFile;
    }

    public void setnameFile(String name) {
        this.nameFile = name;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
