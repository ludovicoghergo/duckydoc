package com.example.duckydoc.DAO;

import java.util.List;

public class Query {
    private long id;
    private User user;
    private String title;
    private String text;
    private List<Answer> answers;
    private int date;

    public Query(long id, User user, String title, String text,  int date, List<Answer> answers) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.text = text;
        this.date = date;
        this.answers = answers;
    }

    public Query(User user, String title, String text, int date) {
        this.id = 0;
        this.user = user;
        this.title = title;
        this.text = text;
        this.date = date;
        this.answers = null;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }
}
