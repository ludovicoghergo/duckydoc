package com.duckydoc.utenti.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "idGoogle")
    private String idGoogle;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "surname")
    private String surname;

    @Column(name = "isMod")
    private boolean isMod;

    @OneToMany(mappedBy = "user")
    private List<Report> reports;

    public Utente(String idGoogle, String name, String email, String surname, boolean isMod) {
        this.idGoogle = idGoogle;
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.isMod = isMod;
    }

    public Utente() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isMod() {
        return isMod;
    }

    public void setMod(boolean mod) {
        this.isMod = mod;
    }
}
