package com.DuckyDoc.Gateway.appunti.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Review {
    private long id;
    private User user;
    private int vote;
    private String text;
    private int data;
    private Document document;
}
