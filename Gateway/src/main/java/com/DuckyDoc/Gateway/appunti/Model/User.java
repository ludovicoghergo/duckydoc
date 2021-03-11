package com.DuckyDoc.Gateway.appunti.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor

public class User {
    private long id;
    private String username;
    private List<Document> documents;
    private List<Review> reviews;
}

