package com.duckydoc.utenti.controller;

import com.duckydoc.utenti.model.Utente;
import com.duckydoc.utenti.rabbit.Runner;
import com.duckydoc.utenti.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UtenteController {
    @Autowired
    UtenteRepository repository;
    @Autowired
    Runner runner;

    @GetMapping("/utenti")
    public List<Utente> getAllUtenti() {
        List<Utente> utenti = new ArrayList<>();
        repository.findAll().forEach(utenti::add);
        return utenti;
    }

    @GetMapping("/utenti/{idUtente}")
    public Utente getUtente(@PathVariable long idUtente) {
        Utente utente = repository.findById(idUtente);
        return utente;
    }

    @PostMapping("utenti/create")
    public Utente postUtente(@RequestBody Utente utente) {
        repository.save(utente);
        runner.sendMessage(utente);
        return utente;
    }
}
