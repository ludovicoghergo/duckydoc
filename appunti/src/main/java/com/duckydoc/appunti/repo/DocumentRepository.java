package com.duckydoc.appunti.repo;

import com.duckydoc.appunti.model.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    Document findById(long id);
    List<Document> findByTitle(String title);
    List<Document> findByUniversity(String university);
    List<Document> findByCourse(String course);
    List<Document> findByUserId(long user_id);
}
