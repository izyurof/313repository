package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showUsersInfo(Model model) {
        model.addAttribute("allusers", userService.getAllUsers());
        return "usersinfo";
    }

    @GetMapping("/user/new")
    public String createNewUser( Model model) {
        model.addAttribute("newuser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute(name = "newuser") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.findRoleByName(role.getRole()).get());
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("edituser", userService.findUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute(name = "edituser") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.findRoleByName(role.getRole()).get());
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("deleteuser", userService.findUserById(id));
        System.out.println(userService.findUserById(id).getName());
        return "delete";
    }

    @DeleteMapping("/user/{id}")
    public String removeUser(@ModelAttribute(name = "deleteuser") User user) {
        System.out.println(user.getId());
        System.out.println(user.getName());
        userService.deleteUser(user);
        return "redirect:/admin";
    }
}
