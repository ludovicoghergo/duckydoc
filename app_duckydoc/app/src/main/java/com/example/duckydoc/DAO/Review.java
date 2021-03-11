package com.example.duckydoc.DAO;

public class Review {
    private long id;
    private User user;
    private int vote;
    private String text;
    private int data;
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
}
