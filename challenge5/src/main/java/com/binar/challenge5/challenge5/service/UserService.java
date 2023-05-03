package com.binar.challenge5.challenge5.service;

import com.binar.challenge5.challenge5.model.User;
import com.binar.challenge5.challenge5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> getUsers() {
        return repository.findAll();
    }

    private static String getRand() {
        String saltChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rand =  new Random();
        while (salt.length() < 18) {
            int index = (int) (rand.nextFloat() * saltChar.length());
            salt.append(saltChar.charAt(index));
        }

        return salt.toString();
    }

    // testing
    public String addUser(String username, String email, String password) {
        Optional<User> userByEmail = repository.findUserByEmail(email);
        if (userByEmail.isPresent()) throw new IllegalArgumentException("user with email " + email + " already exists");

        User user = new User(username, email, password, getRand());
        repository.save(user);
        return "user with username " + user.getUsername() + " added..!";
    }

    @Transactional
    public String updateUser(Long userId, String username, String email, String password) {
        User user = repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("data with id " + userId + " does not exists"));

        if (!Objects.equals(user.getUsername(), username)
                && username.length() > 0) {
            user.setUsername(username);
        }

        if (!email.isEmpty() && !Objects.equals(email, user.getEmail())) {
            user.setEmail(email);
        }

        if (password.length() > 0 && !Objects.equals(password, user.getPassword())) {
            user.setPassword(password);
        }

        return "data berhasil di update";
    }

    @Transactional
    public String deleteUser(Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user with id " + userId + " does not exists"));
        repository.deleteById(userId);
        return "data with id " + userId + " successfuly delete...!";
    }

}
