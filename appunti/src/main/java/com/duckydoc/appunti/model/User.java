package com.duckydoc.appunti.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Document> documents;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setIdUser(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Answer[id=" + id + ", username=" + username + "]";
    }
}

