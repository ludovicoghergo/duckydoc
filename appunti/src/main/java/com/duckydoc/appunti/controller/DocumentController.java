package com.duckydoc.appunti.controller;

import java.util.ArrayList;
import java.util.List;

import com.duckydoc.appunti.FileService.FileService;
import com.duckydoc.appunti.model.Document;
import com.duckydoc.appunti.model.User;
import com.duckydoc.appunti.repo.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class DocumentController {

    private final FileService fileService;

    @Autowired
    public DocumentController(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    DocumentRepository repository;

    @GetMapping("/documents")
    public List<Document> getAllDocument() {
        System.out.println("Get all documents...");
        List<Document> documents = new ArrayList<>();
        repository.findAll().forEach(documents::add);
        return documents;
    }

    @GetMapping(value = "/documents/user/{user_id}")
    public List<Document> getUserDocument(@PathVariable int user_id) {
        System.out.println("Get user documents...");
        List<Document> documents = repository.findByUserId(user_id);
        return documents;
    }

    @PostMapping(value = "/documents/create")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("price") int price,
            @RequestParam("date") int creationData, @RequestParam("desc") String description,
            @RequestParam("university") String university, @RequestParam("year") int year,
            @RequestParam("course") String course, @RequestParam("userId") long userId,
            @RequestParam("userId") String username, @RequestParam("title") String title) {
        try {
            fileService.save(file, price, creationData, description, university, year, course, userId, username, title);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping("/documents/{university}/{course}/{tipologia}/{anno}")
    public List<Document> filterDocument(@PathVariable String university, @PathVariable String course,
            @PathVariable String tipologia, @PathVariable String anno) {
        List<Document> results;

        if (university.equals("null")) {
            university = "";
        }
        if (course.equals("null")) {
            course = "";
        }
        if (tipologia.equals("null")) {
            tipologia = "";
        }
        if (anno.equals("null")) {
            results = repository.findByUniversityContainsAndCourseContainsAndFormatContains(university, course,
                    tipologia);
        } else {
            results = repository.findByUniversityContainsAndCourseContainsAndFormatContainsAndYear(university, course,
                    tipologia, Integer.parseInt(anno));
        }

        return results;
    }

    @GetMapping("/documents/{documentId}")
    public Document getDocumentById(@PathVariable int documentId) {
        return repository.findById(documentId);
    }
}
