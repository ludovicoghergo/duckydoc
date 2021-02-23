package com.DuckyDoc.QA.Repo;

import com.DuckyDoc.QA.Model.Query;
import com.DuckyDoc.QA.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueryRepository extends CrudRepository<Query, Long> {
    List<Query> findByTextContaining(String key);
    List<Query> findByUserId(long user_id);
}