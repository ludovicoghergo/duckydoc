package com.DuckyDoc.QA.Controller;

import com.DuckyDoc.QA.Model.Answer;
import com.DuckyDoc.QA.Model.Query;
import com.DuckyDoc.QA.Repo.QueryRepository;
import com.DuckyDoc.QA.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    QueryRepository repository;

    @GetMapping("/queries")
    public List<Query> getAllQueries() {
        System.out.println("Get all Queries...");

        List<Query> queries = new ArrayList<>();
        repository.findAll().forEach(queries::add);

        for (int i = 0; i < queries.size(); i++) {
            queries.get(i).setAnswers(null);
        }

        return queries;
    }

    @GetMapping(value = "queries/user/{id_user}")
    public List<Query> findByUser(@PathVariable long id_user) {
        System.out.println("Get user Queries...");
        List<Query> queries = repository.findByUserId(id_user);

        for (int i = 0; i < queries.size(); i++) {
            queries.get(i).setAnswers(null);
        }

        return queries;
    }

    @GetMapping(value = "queries/{id}")
    public Query findById(@PathVariable long id) {
        System.out.println("Get Query from id...");
        Optional<Query> query = repository.findById(id);

        if (query.isPresent()) {
            Query _query = query.get();

            List<Answer> answers = _query.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).setQuery(null);
            }

            return _query;
        } else {
            return null;
        }
    }

    @GetMapping(value = "queries/find/{key}")
    public List<Query> findByText(@PathVariable String key) {
        List<Query> queries = repository.findByTextContaining(key);
        return queries;
    }

    @PostMapping(value = "/queries/create")
    public Query postQuery(@RequestBody Query query) {
        Query _query = repository.save(query);
        return query;
    }

    // UTILE??
    @DeleteMapping("/queries/delete/{id}")
    public ResponseEntity<String> deleteQuery(@PathVariable("id") long id) {
        System.out.println("Delete Query with ID = " + id + "...");

        repository.deleteById(id);
        return new ResponseEntity<>("Query has been deleted!", HttpStatus.OK);
    }

    // UTILE??
    @DeleteMapping("/queries/delete")
    public ResponseEntity<String> deleteAllQueries() {
        System.out.println("Delete All Queries...");

        repository.deleteAll();
        return new ResponseEntity<>("All Queries have been deleted!", HttpStatus.OK);
    }

    // UTILE??
    @PutMapping("/queries/setText/{id}")
    public ResponseEntity<Query> updateQuery(@PathVariable("id") long id, @RequestBody String newText) {
        System.out.println("Update Query with ID = " + id + "...");

        Optional<Query> query = repository.findById(id);

        if (query.isPresent()) {
            Query _query = query.get();
            _query.setText(newText);
            return new ResponseEntity<>(repository.save(_query), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
