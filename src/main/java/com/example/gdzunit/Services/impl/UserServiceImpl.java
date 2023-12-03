package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Exceptions.UserNotFoundException;
import com.example.gdzunit.Repositories.UserRepository;
import com.example.gdzunit.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byUsername = userRepository.findByUsername(username);
        log.error("Получен юзер" + byUsername);
        return byUsername.orElseThrow(()-> new UsernameNotFoundException("Юзер не найден!"));
    }

    @Override
    public void saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername()).get();


        try {
            if (userFromDB != null) {
                throw new UserNotFoundException("Пользователь уже существует");
            }

            Set<Role> roles = new HashSet<>();
            roles.add(new Role("USER"));
            user.setRoles(roles);

            userRepository.save(user);
        } catch (UserNotFoundException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
