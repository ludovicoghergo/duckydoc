package com.DuckyDoc.Gateway.appunti.Controller;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.DuckyDoc.Gateway.appunti.Model.User;
import com.DuckyDoc.Gateway.appunti.Model.Document;
import com.DuckyDoc.Gateway.appunti.Model.DocumentApp;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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
    public List<Document> getDocument(@PathVariable(value = "user_id") int user_id) {
        System.out.println("Gateway user document");
        ResponseEntity<List<Document>> response =  restTemplate
                .exchange(ip+"8081/documents/user/"+user_id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "documents/createapp")
    public boolean postDocument(@RequestBody Document document) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> test = new HttpEntity<Object>(document, requestHeaders);
        ResponseEntity<Boolean> response = restTemplate.exchange(ip+"8081/documents/createapp", HttpMethod.POST, test, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @PostMapping(value = "documents/create")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("document") String doc) {
        DocumentApp documentApp = new DocumentApp(file,doc);
        Gson gson = new Gson();
        JsonObject data = new JsonParser().parse(doc).getAsJsonObject();

        Document fileEntity = new Document();
        fileEntity.setTitle(gson.fromJson(data.get("title"), String.class));
        fileEntity.setnameFile(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setFormat(file.getContentType());
        try {
            fileEntity.setData(file.getBytes());
        } catch (IOException e) {
            //TODO: handle exception
        }
        
        fileEntity.setSize(file.getSize());
        fileEntity.setPrice(gson.fromJson(data.get("price"), int.class));
        fileEntity.setDescription(gson.fromJson(data.get("desc"), String.class));
        fileEntity.setUniversity(gson.fromJson(data.get("university"), String.class));
        fileEntity.setCreationData(gson.fromJson(data.get("date"), int.class));
        fileEntity.setYear(gson.fromJson(data.get("year"), int.class));
        fileEntity.setCourse(gson.fromJson(data.get("course"), String.class));
        fileEntity.setUser(gson.fromJson(data.get("user"), User.class));

        HttpEntity<Document> requestEntity = new HttpEntity<>(fileEntity, null);
        ResponseEntity<String> response = restTemplate.exchange(ip+"8081/documents/create", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});
        return response;
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

    @GetMapping("/documents/download/{documentId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable long documentId) {
        System.out.println("Gateway download document...");
        ResponseEntity<Document> respDoc =  restTemplate
                .exchange(ip+"8081/documents/" + documentId, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        Document d = respDoc.getBody();

        ResponseEntity<byte[]> respByte =  restTemplate
                .exchange(ip+"8081/documents/download/" + documentId, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        //System.out.println(respByte.getBody().length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + d.getNameFile() + "\"")
                .contentType(MediaType.valueOf(d.getFormat())).body(respByte.getBody());
    }
}


