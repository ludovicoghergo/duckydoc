package com.duckydoc.appunti.repo;

import com.duckydoc.appunti.model.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends CrudRepository<Document, Long> {
        Optional<Document> findById(long id);

        List<Document> findByUserId(long user_id);

        List<Document> findByUniversityContainsAndCourseContainsAndFormatContainsAndYear(String university,
                        String course, String format, int year);

        List<Document> findByUniversityContainsAndCourseContainsAndFormatContains(String university, String course,
                        String format);

}
