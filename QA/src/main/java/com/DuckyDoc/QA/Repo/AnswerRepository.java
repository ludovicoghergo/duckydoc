package com.DuckyDoc.QA.Repo;

import com.DuckyDoc.QA.Model.Answer;
import com.DuckyDoc.QA.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQueryId(long queryId);
    List<Answer> findByUserId(long user_id);
}