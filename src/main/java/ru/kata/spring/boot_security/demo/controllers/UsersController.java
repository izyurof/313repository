package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UsersController {
    private UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserInfo(Model model, Principal principal) {
        model.addAttribute("user", userService.findUserByUsername(principal.getName()).get());
        return "userinfo";
    }


}
