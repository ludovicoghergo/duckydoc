package com.DuckyDoc.Gateway.QA.Controller;

import com.DuckyDoc.Gateway.QA.Model.Answer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class AnswerController {

    private final RestTemplate restTemplate;
    String ip = "http://192.168.1.28:";

    public AnswerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/answers")
    public List<Answer> getAllAnswers() {
        System.out.println("Gateway answers...");
        ResponseEntity<List<Answer>> response =  restTemplate
                .exchange(ip+"8082/answers", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping("queries/{queryId}/answers")
    public List<Answer> getAnswers(@PathVariable(value = "queryId") Long queryId) {
        System.out.println("Gateway query answers...");
        ResponseEntity<List<Answer>> response =  restTemplate
                .exchange(ip+"8082/queries/" + queryId + "/answers", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "answers/{id}")
    public Answer findById(@PathVariable long id) {
        System.out.println("Gateway id answer...");
        ResponseEntity<Answer> response =  restTemplate
                .exchange(ip+"8082/answers/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "answers/user/{id_user}")
    public List<Answer> findByUser(@PathVariable long id_user) {
        System.out.println("Gateway user answers...");
        ResponseEntity<List<Answer>> response =  restTemplate
                .exchange(ip+"8082/answers/user/" + id_user, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "answers/create")
    public Answer postAnswer(@RequestBody Answer answer) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(answer, requestHeaders);
        ResponseEntity<Answer> response = restTemplate.exchange(ip+"8082/answers/create", HttpMethod.POST, test, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    // UTILE??
    @DeleteMapping("/answers/delete/{id}")
    public String deleteAnswer(@PathVariable("id") long id) {
        System.out.println("Gateway delete answer...");
        ResponseEntity<String> response =  restTemplate
                .exchange(ip+"8082/answers/delete/" + id, HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
    //cambiare IP!!!!!
    // UTILE??
    @PutMapping("/answers/setText/{id}")
    public Answer updateAnswer(@PathVariable("id") long id, @RequestBody String newText) {
        System.out.println("Gateway update answer...");
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(newText, requestHeaders);
        ResponseEntity<Answer> response =  restTemplate
                .exchange(ip+"8082/answers/setText/" + id, HttpMethod.PUT, test,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PutMapping("/answers/correct/{id}")
    public Answer correctAnswer(@PathVariable("id") long id) {
        System.out.println("Gateway correct answer...");
        ResponseEntity<Answer> response =  restTemplate
                .exchange(ip+"8082/answers/correct/" + id, HttpMethod.PUT, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}
