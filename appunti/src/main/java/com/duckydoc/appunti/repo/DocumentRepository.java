package com.duckydoc.appunti.repo;

import com.duckydoc.appunti.model.Document;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    Document findById(long id);
    List<Document> findByUserId(long user_id);
    List<Document> findByUniversityContainsAndCourseContainsAndFormatContainsAndYear(String university, String course, String format, int year);
    List<Document> findByUniversityContainsAndCourseContainsAndFormatContains(String university, String course, String format);

    List<Document> findAll(Example<Document> exampleQuery);
}
