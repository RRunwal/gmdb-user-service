package com.galvanize.Services;

import com.galvanize.Models.User;
import com.galvanize.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GmdbUserService {
    private final UserRepository userRepository;

    @Autowired
    public GmdbUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validateUser(String userid, String password) {
        List<User> users = userRepository.findUserModelByEmail(userid);
        User user = users.stream()
                .filter(u -> u.getEmail().equals(userid) && u.getPassword().equals(password))
                .findFirst().orElse(null);
        if(user == null) return null;
        else return user;
    }

    public boolean createUser(User user){
        if(user.getPassword().equals(user.getRepeatPassword())){
            final User save = userRepository.save(user);
            return true;
        } else return false;
    }

    public String getPassword(String email){
        User user = userRepository.findUserByEmail(email);
        return user.getPassword();
    }

    public int logout(User user) {
        if (user != null && user.getId() != null) {
            return 400;
        }
        else {
            return 200;
        }
    }

}
