package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    void saveRole(Role role);
    Role findRoleById(Long id);
    Optional<Role> findRoleByName(String name);
    void deleteRole(Role role);
    List<Role> getAllRoles();
}
