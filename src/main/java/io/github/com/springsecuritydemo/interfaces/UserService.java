package io.github.com.springsecuritydemo.interfaces;

import io.github.com.springsecuritydemo.entities.Product;
import io.github.com.springsecuritydemo.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(User user);
}
