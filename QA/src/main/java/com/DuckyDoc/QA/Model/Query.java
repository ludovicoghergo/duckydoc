package com.DuckyDoc.QA.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "queries")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="userId")
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    public int date;

    @OneToMany(mappedBy = "query")
    private List<Answer> answers;

    public Query(){
    }

    public Query(User user, String text, int date){
        this.user = user;
        this.text = text;
        this.date = date;
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

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}