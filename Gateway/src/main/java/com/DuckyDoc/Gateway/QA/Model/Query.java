package com.DuckyDoc.Gateway.QA.Model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Query {
    private long id;
    private User user;
    private String title;
    private String text;
    public int date;
    private List<Answer> answers;
}