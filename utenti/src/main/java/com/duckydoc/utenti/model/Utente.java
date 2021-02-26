package com.duckydoc.utenti.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "isModeratore")
    private boolean isModeratore;

    @OneToMany(mappedBy = "user")
    private List<Report> reports;

    public Utente(String nome, String email, String password, boolean isModeratore) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.isModeratore = isModeratore;
    }

    public Utente() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isModeratore() {
        return isModeratore;
    }

    public void setModeratore(boolean moderatore) {
        isModeratore = moderatore;
    }
}
