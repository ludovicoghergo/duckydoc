package com.DuckyDoc.Gateway.report.Controller;

import com.DuckyDoc.Gateway.report.model.Report;
import com.DuckyDoc.Gateway.report.model.Utente;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ReportController {

    private final RestTemplate restTemplate;
    String ip="http://192.168.1.28:";

    public ReportController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/reports")
    public List<Report> getAllReport() {
        System.out.println("Gateway reports...");
        ResponseEntity<List<Report>> response =  restTemplate
                .exchange(ip+"8084/reports", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping(value = "reports/{id}")
    public Report findById(@PathVariable long id) {
        System.out.println("Gateway id report...");
        ResponseEntity<Report> response =  restTemplate
                .exchange(ip+"8084/reports/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @GetMapping("reports/{document_id}")
    public List<Report> getReports(@PathVariable(value = "document_Id") Long document_Id) {
        System.out.println("Gateway document reports...");
        ResponseEntity<List<Report>> response =  restTemplate
                .exchange(ip+"8084/reports/" + document_Id , HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "reports/{id_user}/create")
    public Report postReport(@PathVariable("id_user") long id_user, @RequestBody Report report ) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(report, requestHeaders);
        ResponseEntity<Report> response = restTemplate.exchange(ip+"8084/reports/"+id_user+"/create", HttpMethod.POST, test, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}


