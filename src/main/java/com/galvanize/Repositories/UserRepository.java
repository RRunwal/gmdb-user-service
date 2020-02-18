package com.galvanize.Repositories;

import com.galvanize.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findUserModelByEmail(String email);
    User findDistinctById(Long id);
    User findUserByEmail(String email);
}
