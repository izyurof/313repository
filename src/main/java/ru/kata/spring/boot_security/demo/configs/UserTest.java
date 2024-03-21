package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class UserTest {
    private UserService userService;

    @Autowired
    public UserTest(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void loadTestUser() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        User user = new User("user", "userov", (byte) 25, "user@gmail.com");
        User admin = new User("admin", "adminov", (byte) 25, "admin@gmail.com");
        user.setPassword("password");
        user.addRole(roleUser);
        admin.setPassword("password");
        admin.addRole(roleAdmin);
        userService.saveUser(user);
        userService.saveUser(admin);
        user.setRoles(Set.of(roleUser));
        admin.setRoles(Set.of(roleAdmin));
    }
}
