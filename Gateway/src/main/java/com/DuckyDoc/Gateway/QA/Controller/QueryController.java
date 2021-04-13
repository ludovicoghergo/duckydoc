package com.DuckyDoc.Gateway.QA.Controller;

import com.DuckyDoc.Gateway.QA.Model.Query;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class QueryController {

    private final RestTemplate restTemplate;
    String ip = "http://192.168.0.192:";

    public QueryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/queries")
    public List<Query> getAllQueries() {
        System.out.println("Gateway queries...");
        ResponseEntity<List<Query>> response =  restTemplate
                .exchange(ip+"8082/queries", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "queries/user/{id_user}")
    public List<Query> findByUser(@PathVariable long id_user) {
        System.out.println("Gateway user queries...");
        ResponseEntity<List<Query>> response =  restTemplate
                .exchange(ip+"8082/queries/user/" + id_user, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "queries/{id}")
    public Query findById(@PathVariable long id) {
        System.out.println("Gateway id queries...");
        ResponseEntity<Query> response =  restTemplate
                .exchange(ip+"8082/queries/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "queries/find/{key}")
    public List<Query> findByText(@PathVariable String key) {
        System.out.println("Gateway text queries...");
        ResponseEntity<List<Query>> response =  restTemplate
                .exchange(ip+"8082/queries/find/" + key, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "/queries/create")
    public Query postQuery(@RequestBody Query query) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(query, requestHeaders);
        ResponseEntity<Query> response = restTemplate.exchange(ip+"8082/queries/create", HttpMethod.POST, test, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @DeleteMapping("/queries/delete/{id}")
    public String deleteQuery(@PathVariable("id") long id) {
        System.out.println("Gateway delete query...");
        ResponseEntity<String> response =  restTemplate
                .exchange(ip+"8082/queries/delete/" + id, HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PutMapping("/queries/setText/{id}")
    public Query updateQuery(@PathVariable("id") long id, @RequestBody String newText) {
        System.out.println("Gateway upde query...");
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(newText, requestHeaders);
        ResponseEntity<Query> response = restTemplate.exchange(ip+"8082/queries/setText/" + id, HttpMethod.POST, test,
                new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}
