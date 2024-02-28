package io.github.com.springsecuritydemo.interfaces;

import io.github.com.springsecuritydemo.entities.User;

public interface UserService {
    User create(User user);
}
