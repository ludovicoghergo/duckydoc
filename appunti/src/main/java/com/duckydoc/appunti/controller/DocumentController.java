package com.duckydoc.appunti.controller;

import com.duckydoc.appunti.model.Document;
import com.duckydoc.appunti.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    DocumentRepository repository;

    @GetMapping("/documents")
    public List<Document> getAllDocument(){
        System.out.println("Get all documents...");
        List<Document> documents = new ArrayList<>();
        repository.findAll().forEach(documents::add);
        return documents;
    }

    @GetMapping(value = "/documents/user/{user_id}")
    public List<Document> getUserDocument(@PathVariable long user_id){
        System.out.println("Get user documents...");
        List<Document> documents = repository.findByUserId(user_id);
        return documents;
    }

    @PostMapping(value = "/documents/create")
    public Document postDocument(@RequestBody Document document){
        System.out.println("Insert new document...");
        Document _document = repository.save(new Document(document.getTitle(), document.getFormat(), document.getCreationData(), document.getPrice(), document.getDescription(), document.getUniversity(), document.getYear(), document.getCourse(), document.getFileUrl(), document.getUser()));
        return _document;
    }

    @GetMapping("/documents/{university}/{course}/{tipologia}/{anno}")
    public List<Document> filterDocument(@PathVariable String university, @PathVariable String course, @PathVariable String tipologia, @PathVariable String anno){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Document> exampleQuery;

        if(university.equals("null")){
            university = null;
        }
        if(course.equals("null")){
            course = null;
        }
        if(tipologia.equals("null")){
            tipologia = null;
        }
        if(anno.equals("null")){
            exampleQuery = Example.of(new Document(university, course, tipologia), matcher);
        }
        else{
            exampleQuery = Example.of(new Document(university, course, tipologia, Integer.parseInt(anno)), matcher);
        }

        List<Document> results = repository.findAll(exampleQuery);
        return results;
    }

    @GetMapping("/documents/{documentId}")
    public Document getDocumentById (@PathVariable long documentId){
        return repository.findById(documentId);
    }

    @GetMapping("/documents/title/{title}")
    public List<Document> getDocumentsByTitle(@PathVariable String title){
        return repository.findByTitle(title);
    }

    @GetMapping("/documents/university/{university}")
    public List<Document> getDocumentByUniversity(@PathVariable String university){
        return repository.findByUniversity(university);
    }

    @GetMapping("documents/course/{course}")
    public List<Document> getDocumentByCourse(@PathVariable String course){
        return repository.findByCourse(course);
    }


}
