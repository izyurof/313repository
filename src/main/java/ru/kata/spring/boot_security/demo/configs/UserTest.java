package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class UserTest {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    @Transactional(readOnly = true)
    public void loadTestUser() {
        User user = new User("user", "userov", (byte) 25, "user@gmail.com");
        User admin = new User("admin", "adminov", (byte) 23, "admin@gmail.com");
        user.setPassword("$2a$12$iLuHjYDDzdzTHph1gWRFFe8F2TO7o2dG6.UUABtZ7GiP8LTvOwPgO");
        admin.setPassword("$2a$12$iLuHjYDDzdzTHph1gWRFFe8F2TO7o2dG6.UUABtZ7GiP8LTvOwPgO");
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
        user.setRoles(Set.of(roleRepository.findById(1L).get()));
        admin.setRoles(Set.of(roleRepository.findById(1L).get(), roleRepository.findById(2L).get()));
        userRepository.save(user);
        userRepository.save(admin);
    }
}
