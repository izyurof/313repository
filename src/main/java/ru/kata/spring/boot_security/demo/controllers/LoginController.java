package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String errorMessage,
                        @RequestParam(value = "logout", required = false) String logoutMessage, Model model) {
        model.addAttribute("error", errorMessage != null);
        model.addAttribute("logout", logoutMessage != null);
        return "login";
    }
}
