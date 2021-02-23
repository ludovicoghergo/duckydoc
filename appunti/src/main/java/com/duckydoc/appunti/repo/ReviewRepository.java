package com.duckydoc.appunti.repo;

import com.duckydoc.appunti.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByDocumentId(Long documentId);
}
