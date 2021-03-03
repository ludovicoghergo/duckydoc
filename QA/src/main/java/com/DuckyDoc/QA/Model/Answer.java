package com.DuckyDoc.QA.Model;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "idUser")
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    public int date;

    @Column(name = "correct")
    private boolean correct;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "queryId")
    private Query query;

    public Answer() {
    }

    public Answer(User user, Query query, String text, boolean correct, int date) {
        this.user = user;
        this.query = query;
        this.text = text;
        this.correct = correct;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
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

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Answer[id=" + id + ", user=" + user + ", text=" + text + ", corrected=" + correct + ", query="
                + query.getText() + "]";
    }
}