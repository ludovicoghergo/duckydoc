package com.DuckyDoc.Gateway.QA.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer {
    private long id;
    private User user;
    private String text;
    public int date;
    private boolean correct;
    private Query query;
}