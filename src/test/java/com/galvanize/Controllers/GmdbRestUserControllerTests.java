package com.galvanize.Controllers;

//DO NOT FORGET to add these in order to use get(), pritn() etc. methods in your MockMcv tests
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.galvanize.Repositories.UserRepository;
import com.galvanize.Services.GmdbUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import javax.transaction.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GmdbRestUserControllerTests {

    @Autowired
    GmdbUserService service;

    @Autowired
    UserRepository repository;

    @Autowired
    MockMvc mvc;


    @Test
    void testPostLoginSucess() throws Exception {
        String json = "{\"email\":\"rrunwal@aol.com\",\"password\":\"rrunwal1\"}";
        mvc.perform(post("/login")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("rrunwal@aol.com")));
    }

    @Test
    void testUnexpectedExceptionsAreCaughtPostLogin() throws Exception {
        String json = "{\"email\":\"rrunwal@aol.com\",\"password\":\"rrunwal\"}";
        mvc.perform(post("/login")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("errorMessage", "Invalid Credentials"));
    }

    @Test
    void testPostLogoutSucess() throws Exception {
        String json = "{\"email\":\"rrunwal@aol.com\",\"password\":\"rrunwal1\"}";

        mvc.perform(post("/logout")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testPostLogoutFailure() throws Exception {
        String json = "{\"email\":\"rrunwal@aol.com\",\"password\":\"rrunwal\"}";

        mvc.perform(post("/logout")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterSuccess() throws Exception {
        String json = "{\"email\":\"rrunwal2@aol.com\",\"password\":\"rrunwal2\",\"repeatPassword\":\"rrunwal2\",\"screenName\":\"rrunwal2\"}";

        mvc.perform(post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("rrunwal2@aol.com")));
    }

    @Test
    void testRegisterFailureUserAlreadyExists() throws Exception {
        String json = "{\"email\":\"rrunwal@aol.com\",\"password\":\"rrunwal1\",\"repeatPassword\":\"rrunwal1\",\"screenName\":\"rrunwal\"}";
        mvc.perform(post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("errorMessage", "Could not create user: user already exists"));
    }

    @Test
    void testRegisterFailurePasswordsDoNotMatch() throws Exception {
        String json = "{\"email\":\"rrunwal3@aol.com\",\"password\":\"rrunwal3\",\"repeatPassword\":\"rrunwal\",\"screenName\":\"rrunwal3\"}";
        mvc.perform(post("/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("errorMessage", "Could not create user: passwords don't match"));
    }
}
