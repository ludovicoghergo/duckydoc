package com.duckydoc.appunti.controller;

import com.duckydoc.appunti.model.Document;
import com.duckydoc.appunti.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("")
public class DocumentController {

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
    public List<Document> getUserDocument(@PathVariable long user_id) {
        System.out.println("Get user documents...");
        List<Document> documents = repository.findByUserId(user_id);
        return documents;
    }

    @PostMapping(value = "/documents/create")
    public Document postDocument(@RequestBody Document document) {
        System.out.println("Insert new document...");
        Document _document = repository.save(new Document(document.getTitle(), document.getFormat(),
                document.getCreationData(), document.getPrice(), document.getDescription(), document.getUniversity(),
                document.getYear(), document.getCourse(), document.getFileUrl(), document.getUser()));
        return _document;
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
    public Document getDocumentById(@PathVariable long documentId) {
        return repository.findById(documentId);
    }
}
