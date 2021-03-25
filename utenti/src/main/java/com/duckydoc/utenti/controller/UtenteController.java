package com.duckydoc.utenti.controller;

import com.duckydoc.utenti.model.Utente;
import com.duckydoc.utenti.rabbit.Runner;
import com.duckydoc.utenti.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("")
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
    public Utente getUtente(@PathVariable String idUtente) {
        System.out.println("Dentro al microservizo ludo");
        Utente utente = repository.findByIdGoogle(idUtente);
        return utente;
    }

    @PostMapping("utenti/create")
    public Utente postUtente(@RequestBody Utente utente) {
        System.out.println(utente.getCredits());
        Utente u = repository.save(utente);
        runner.sendMessage(utente);
        return u;
    }

    @PutMapping("utenti/updatecredit")
    public ResponseEntity<Utente> updateQuery(@RequestParam("UserId") int userId,
    @RequestParam("credits") int credits) {
        System.out.println("Update User with ID = " + userId+ "...");
        Long id= new Long(userId);
        Optional<Utente> query = repository.findById(id);

        if (query.isPresent()) {
            Utente _query = query.get();
            _query.setCredits(credits);
            return new ResponseEntity<>(repository.save(_query), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
}
