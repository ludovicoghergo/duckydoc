package com.DuckyDoc.Gateway.appunti.Controller;

import com.DuckyDoc.Gateway.appunti.Model.Review;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ReviewController {

    private final RestTemplate restTemplate;
    String ip="http://192.168.1.28:";

    public ReviewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        System.out.println("Gateway reviews...");
        ResponseEntity<List<Review>> response =  restTemplate
                .exchange(ip+"8081/reviews", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping("/documents/{documentId}/reviews")
    public List<Review> getReviewsByDocument(@PathVariable long documentId) {
        System.out.println("Gateway reviews by document..");
        ResponseEntity<List<Review>> response =  restTemplate
                .exchange(ip+"8081/documents/"+documentId+"/reviews", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "/reviews/create")
    public Review postReview(@RequestBody Review review) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(review, requestHeaders);
        ResponseEntity<Review> response = restTemplate.exchange(ip+"8081/reviews/create", HttpMethod.POST, test, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}


