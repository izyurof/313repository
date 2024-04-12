package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UsersController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;


    @Autowired
    public UsersController(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping
    public String showUserInfo(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("roleAdmin", roleRepository.findById(2L).get());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("roleUser", roleRepository.findById(1L).get());
        model.addAttribute("allUsers", userRepository.findAll());
        return "user";
    }

}
