package com.DuckyDoc.Gateway.appunti.Controller;

import com.DuckyDoc.Gateway.appunti.Model.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class DocumentController {

    private final RestTemplate restTemplate;
    String ip = "http://192.168.1.28:";

    public DocumentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/documents")
    public List<Document> getAllDocument() {
        System.out.println("Gateway all documents...");
        ResponseEntity<List<Document>> response =  restTemplate
                .exchange(ip+"8081/documents", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping("documents/user/{user_id}")
    public List<Document> getDocument(@PathVariable(value = "user_id") Long user_id) {
        System.out.println("Gateway user document");
        ResponseEntity<List<Document>> response =  restTemplate
                .exchange(ip+"8081/document/user/"+user_id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "documents/create")
    public Document postDocument(@RequestBody Document document) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(document, requestHeaders);
        ResponseEntity<Document> response = restTemplate.exchange(ip+"8081/documents/create", HttpMethod.POST, test, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "/documents/{university}/{course}/{tipologia}/{anno}")
    public List<Document> filterDocument(@PathVariable String university, @PathVariable String course,
                                         @PathVariable String tipologia, @PathVariable String anno) {
        System.out.println("Gateway filtered document...");
        ResponseEntity<List<Document>> response =  restTemplate
                .exchange(ip+"8081/documents/" + university+"/"+course+"/"+tipologia+"/"+anno, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "documents/{documentId}")
    public Document getDocumentById(@PathVariable long documentId) {
        System.out.println("Gateway id document...");
        ResponseEntity<Document> response =  restTemplate
                .exchange(ip+"8081/documents/" + documentId, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}


