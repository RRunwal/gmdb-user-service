package com.galvanize.Repositories;

import com.galvanize.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserModelByEmail(String email);
//    User findDistinctById(Long id);
    User findUserByEmail(String email);
}
