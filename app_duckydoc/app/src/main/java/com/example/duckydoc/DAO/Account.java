package com.example.duckydoc.DAO;

import androidx.annotation.NonNull;

public class Account {
    private long idUser;
    private String email;
    private String nome;
    private String password;

    public Account(int idUser, String email, String nome, String password) {
        this.idUser = idUser;
        this.email = email;
        this.nome = nome;
        this.password = password;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    public String toString(){
        return nome + ", email:" + email + ", password:" + password;
    }
}
