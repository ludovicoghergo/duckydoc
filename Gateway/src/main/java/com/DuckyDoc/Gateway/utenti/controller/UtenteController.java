package com.DuckyDoc.Gateway.utenti.controller;

import com.DuckyDoc.Gateway.utenti.model.Intern;
import com.DuckyDoc.Gateway.utenti.model.Utente;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UtenteController {

    private final RestTemplate restTemplate;
    String ip = "http://192.168.0.192:";


    public UtenteController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/utenti")
    public List<Utente> getAllUtenti() {
        System.out.println("Gateway utenti...");
        ResponseEntity<List<Utente>> response =  restTemplate
                .exchange(ip+"8083/utenti", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping("/utenti/{idUtente}")
    public Utente getUtente(@PathVariable(value = "idUtente") String idUtente) {
        System.out.println("Gateway query utenti");
        ResponseEntity<Utente> response =  restTemplate
                .exchange(ip+"8083/utenti/" + idUtente, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping("/utenti/alt/{idUtente}")
    public Utente getUtenteAlt(@PathVariable(value = "idUtente") String idUtente) {
        System.out.println("Gateway utenti by idUser");
        Long id = new Long(idUtente);
        ResponseEntity<Utente> response =  restTemplate
                .exchange(ip+"8083/utenti/alt/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "utenti/create")
    public Utente postUtente(@RequestBody Utente utente) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(utente, requestHeaders);
        ResponseEntity<Utente> response = restTemplate.exchange(ip+"8083/utenti/create", HttpMethod.POST, test, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }


    @PutMapping("utenti/{idUtente}/updatecredit")
    public Utente updateUSer(@PathVariable(value = "idUtente") int idUtente, @RequestBody Intern credits) {
        System.out.println("Gateway update credits");
        long id = idUtente;
        int credits_int = credits.getCredits();
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> credits_imp = new HttpEntity<Object>(credits_int, requestHeaders);
        ResponseEntity<Utente> response = restTemplate.exchange(ip+"8083/utenti/"+id+"/updatecredit", HttpMethod.PUT, credits_imp, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}
