package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    void deleteUser(User user);
    User getUser(long id) throws UserNotFoundException;
    List<User> getAllUsers();
    void updateUser(User user) throws UserNotFoundException;
}
