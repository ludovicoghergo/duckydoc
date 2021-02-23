package com.example.duckydoc.DAO;

public class Answer {
    private long id;
    private User user;
    private String text;
    private boolean correct;
    private Query query;
    private int date;

    public Answer(long id, User user, String text, int date, boolean correct, Query query) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = date;
        this.correct = correct;
        this.query = query;
    }

    public Answer(long id, User user, String text, int date, boolean correct) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = date;
        this.correct = correct;
        this.query = null;
    }

    public Answer(User user, String text, int date, boolean correct, Query query) {
        this.user = user;
        this.text = text;
        this.date = date;
        this.correct = correct;
        this.query = query;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
