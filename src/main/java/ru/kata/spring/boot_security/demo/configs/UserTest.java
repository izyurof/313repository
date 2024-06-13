package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class UserTest {
    private final UserService userService;
    private final RoleService roleService;

    public UserTest(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostConstruct
    @Transactional
    public void loadTestUser() {
        User user = new User("user", "userov", (byte) 25, "user@gmail.com");
        User admin = new User("admin", "adminov", (byte) 23, "admin@gmail.com");
        user.setPassword("password");
        admin.setPassword("password");
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleService.saveRole(roleUser);
        roleService.saveRole(roleAdmin);
        user.setRoles(Set.of(roleService.findRoleByName(roleUser.getRole()).get()));
        admin.setRoles(Set.of(roleService.findRoleByName(roleUser.getRole()).get(), roleService.findRoleByName(roleAdmin.getRole()).get()));
        userService.saveUser(user);
        userService.saveUser(admin);
    }

}
