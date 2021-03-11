package com.duckydoc.appunti.controller;

import com.duckydoc.appunti.model.Review;
import com.duckydoc.appunti.repo.DocumentRepository;
import com.duckydoc.appunti.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("")
public class ReviewController {

    @Autowired
    ReviewRepository repository;

    @Autowired
    DocumentRepository documentRepository;

    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        repository.findAll().forEach(reviews::add);
        return reviews;
    }

    @GetMapping("/documents/{documentId}/reviews")
    public List<Review> getReviewsByDocument(@PathVariable long documentId) {
        List<Review> reviews = repository.findByDocumentId(documentId);
        return reviews;
    }

    @PostMapping(value = "/reviews/create")
    public Review postReview(@RequestBody Review review) {
        Review _review = repository.save(new Review(review.getVote(), review.getText(), review.getData(),
                review.getUser(), review.getDocument()));
        return _review;
    }

}
