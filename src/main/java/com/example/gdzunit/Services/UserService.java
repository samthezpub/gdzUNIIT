package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    void deleteUser(User user);
    User getUser(long id);
}