package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRoleById(Long id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return roleDao.findRoleByName(name);
    }

    @Override
    @Transactional
    public void deleteRole(Role role) {
        roleDao.deleteRole(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

}
