package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Exceptions.UserNotFoundException;
import com.example.gdzunit.Exceptions.VariantNotFoundedException;
import com.example.gdzunit.Repositories.RoleRepository;
import com.example.gdzunit.Repositories.UserRepository;
import com.example.gdzunit.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VariantServiceImpl variantService;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byUsername = userRepository.findByUsername(username);
        return byUsername.orElseThrow(()-> new UsernameNotFoundException("Юзер не найден!"));
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loadUserByUsername(principal.toString());
    }

    @Override
    public void saveUser(User user) {
        boolean isUserFromDB = userRepository.findByUsername(user.getUsername()).isEmpty();


        try {
            if (!isUserFromDB) {
                throw new UserNotFoundException("Пользователь уже существует");
            }

            Set<Role> roles = user.getRoles();
            roles.add(roleRepository.findRoleByName("USER"));

            user.setRoles(roles);
            Long id = user.getVariant().getId();

            try {
                user.setVariant(variantService.findVariantById(id));
            } catch (VariantNotFoundedException e) {
                throw new RuntimeException(e);
            }

//            user.setRegisterDate(LocalDateTime.now());


            userRepository.save(user);
        } catch (UserNotFoundException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUser(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(User user) throws UserNotFoundException {
        User userFromDB = userRepository.findById(user.getId()).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден!")
        );
        user.setRoles(userFromDB.getRoles());
        user.setComments(userFromDB.getComments());

        userRepository.saveAndFlush(user);
    }

}
