package com.galvanize.Repositories;

import com.galvanize.Models.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    //NOTE: test data has been loaded into the user table in gmdb database.
    //please see file "download.sql" in gmdb-movies-project
    @Test
    void findUserByEmail() {
        User user = userRepository.findUserByEmail("rrunwal@aol.com");
        assertEquals("rrunwal1", user.getPassword());
    }

    @Test
    void findUserModelByEmail() {
        List<User> users = userRepository.findUserModelByEmail("rrunwal@aol.com");
        assertEquals(1, users.size());
    }
}
