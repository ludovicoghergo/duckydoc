package com.duckydoc.appunti.model;

import javax.persistence.*;

@Entity
@Table(name = "review")

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="userId")
    private User user;

    @Column(name = "vote")
    private int vote;

    @Column(name = "text")
    private String text;

    @Column(name = "data")
    private int data;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "documenId")
    private Document document;

    public Review(int vote, String text, int data, User user, Document document) {
        this.vote = vote;
        this.text = text;
        this.data = data;
        this.user = user;
        this.document = document;
    }

    public Review() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
