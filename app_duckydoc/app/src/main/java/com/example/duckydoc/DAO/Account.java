package com.example.duckydoc.DAO;

public class Account {
    private long id;
    private String idGoogle;
    private String email;
    private String name;
    private String surname;
    private int credits;

    public Account(String idGoogle, String email, String name, String surname, int credits) {
        this.idGoogle = idGoogle;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.credits = credits;
    }

    public Account(int idUser, String idGoogle, String email, String name, String surname, int credits) {
        this.id = idUser;
        this.idGoogle = idGoogle;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.credits = credits;
    }

    public Account(long idUser) {
        this.id = idUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public long getIdUser() {
        return id;
    }

    public void setIdUser(long idUser) {
        this.id = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
