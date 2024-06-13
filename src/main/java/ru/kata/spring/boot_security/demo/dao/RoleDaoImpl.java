package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findRoleByName(String name) {
        Role role = entityManager.createQuery("select r from Role r where r.role = :role", Role.class).setParameter("role", name).getSingleResult();
        return Optional.of(Optional.ofNullable(role).orElseThrow(() -> new EntityNotFoundException(String.format("Role with name '%s' doesn't exists", name))));
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        entityManager.persist(role);
    }
}
