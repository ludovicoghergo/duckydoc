package com.DuckyDoc.Gateway.QA.Model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private long id;
    private String username;
    private List<Query> queries;
    private List<Answer> answers;
}
