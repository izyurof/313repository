package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;


    @Autowired
    public AdminController(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }


    @GetMapping()
    public String adminPanel(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("roleAdmin", roleRepository.findById(2L).get());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("roleUser", roleRepository.findById(1L).get());
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @GetMapping("{id}")
    public String userInfo(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        return "admin";
    }

    @PatchMapping("{id}")
    public String updateUser(@ModelAttribute(name = "user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String deleteUser(@ModelAttribute(name = "user") User user) {
        userRepository.delete(user);
        return "redirect:/admin";
    }

    @PostMapping()
    public String addUser(@ModelAttribute(name = "newUser") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }


//    @GetMapping()
//    public String showUsersInfo(Model model) {
//        model.addAttribute("allusers", userRepository.findAll());
//        return "usersinfo";
//    }
//
//    @GetMapping("/user/new")
//    public String createNewUser( Model model) {
//        model.addAttribute("newuser", new User());
//        model.addAttribute("allRoles", roleRepository.findAll());
//        return "new";
//    }
//
//    @PostMapping()
//    public String saveUser(@ModelAttribute(name = "newuser") User user) {
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/user/{id}/edit")
//    public String editUser(@PathVariable(value = "id") Long id, Model model) {
//        model.addAttribute("edituser", userRepository.findById(id).get());
//        model.addAttribute("allRoles", roleRepository.findAll());
//        return "edit";
//    }
//
//    @PatchMapping("/user/{id}")
//    public String updateUser(@ModelAttribute(name = "edituser") User user) {
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/user/{id}")
//    public String deleteUser(@PathVariable(value = "id") Long id, Model model) {
//        model.addAttribute("deleteuser", userRepository.findById(id).get());
//        return "delete";
//    }
//
//    @DeleteMapping("/user/{id}")
//    public String removeUser(@ModelAttribute(name = "deleteuser") User user) {
//        userRepository.delete(user);
//        return "redirect:/admin";
//    }
}
