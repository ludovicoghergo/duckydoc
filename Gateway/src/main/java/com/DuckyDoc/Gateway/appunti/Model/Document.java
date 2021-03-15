package com.DuckyDoc.Gateway.appunti.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor

public class Document {
    private long id;
    private String title;
    private String nameFile;
    private byte[] data;
    private Long size;
    private String format;
    private int creationData;
    private int price;
    private String description;
    private String university;
    private int year;
    private String course;
    private User user;
    private List<Review> reviews;
}
