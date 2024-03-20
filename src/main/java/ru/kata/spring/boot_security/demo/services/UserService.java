package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    User findUserById(Long id);
    void updateUser(User user);
    void deleteUser(User user);
    List<User> getAllUsers();
    Optional<User> findUserByUsername(String username);
}
