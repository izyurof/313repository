package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UsersController {
    private UserDetailsService userDetailsService;

    @Autowired
    public UsersController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String showUserInfo(Model model, Principal principal) {
        model.addAttribute("user", userDetailsService.loadUserByUsername(principal.getName()));
        return "userinfo";
    }


}
