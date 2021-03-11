package com.example.duckydoc.DAO;

import androidx.annotation.NonNull;

public class Account {
    private long id;
    private String idGoogle;
    private String email;
    private String name;
    private String surname;

    public Account(String idGoogle, String email, String name, String surname) {
        this.idGoogle = idGoogle;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public Account(int idUser, String idGoogle, String email, String name, String surname) {
        this.id = idUser;
        this.idGoogle = idGoogle;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public Account(long idUser) {
        this.id = idUser;
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
