package com.DuckyDoc.QA.Repo;

import com.DuckyDoc.QA.Model.Answer;
import com.DuckyDoc.QA.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);
}
