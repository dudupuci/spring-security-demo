package io.github.com.springsecuritydemo.services;

import io.github.com.springsecuritydemo.entities.User;
import io.github.com.springsecuritydemo.interfaces.UserService;
import io.github.com.springsecuritydemo.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        var userExists = this.repository.findByUsername(user.getUsername());

        if (userExists != null) {
            throw new Error("User already exists");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return this.repository.save(user);
    }
}
