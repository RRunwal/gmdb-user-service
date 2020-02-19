package com.galvanize.Services;

import com.galvanize.Models.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class GmdbUserServiceTests {

    @Autowired
    GmdbUserService gmdbUserService;

    //NOTE: test data has been loaded into the user table in gmdb database.
    //please see file "download.sql" in gmdb-movies-project
    @Test
    void validateUser() {
        User user = gmdbUserService.validateUser("rrunwal@aol.com", "rrunwal");
        System.out.println("\n");
        System.out.println(user);
        System.out.println("\n");
        assertNull(user);
        user = gmdbUserService.validateUser("rrunwal@aol.com", "rrunwal1");
        System.out.println("\n");
        System.out.println(user);
        System.out.println("\n");
        assertNotNull(user);
    }

    @Test
    void createUserSuccess() {
        User user = new User();
        user.setEmail("jkrunwal3@aol.com");
        user.setPassword("jkrunwal3");
        user.setRepeatPassword("jkrunwal3");
        user.setScreenName("jkrunwal3");

        boolean createUserResult = gmdbUserService.createUser(user);
        System.out.println("\n");
        System.out.println(createUserResult);
        System.out.println("\n");
        assertEquals(true, createUserResult);
    }

    @Test
    void createUserFailure() {
        User user = new User();
        user.setEmail("rrunwal3@aol.com");
        user.setPassword("rrunwal3");
        user.setRepeatPassword("rrunwal");
        user.setScreenName("rrunwal3");

        boolean createUserResult = gmdbUserService.createUser(user);
        System.out.println("\n");
        System.out.println(createUserResult);
        System.out.println("\n");
        assertEquals(false, createUserResult);
    }

    @Test
    void getPassword() {
        String password = gmdbUserService.getPassword("rrunwal@aol.com");
        System.out.println("\n");
        System.out.println(password);
        System.out.println("\n");
        assertEquals("rrunwal1", password);
    }

    @Test
    void logoutSuccess() {
        User user = gmdbUserService.validateUser("rrunwal@aol.com", "rrunwal1");
        int logoutResult = gmdbUserService.logout(user);
        assertEquals(400, logoutResult);
    }

    @Test
    void logoutFailure() {
        User user = gmdbUserService.validateUser("rrunwal", "rrunwal1");
        int logoutResult = gmdbUserService.logout(user);
        assertEquals(200, logoutResult);
    }
}
