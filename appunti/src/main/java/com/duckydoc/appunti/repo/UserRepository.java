package com.duckydoc.appunti.repo;

import com.duckydoc.appunti.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);
}
