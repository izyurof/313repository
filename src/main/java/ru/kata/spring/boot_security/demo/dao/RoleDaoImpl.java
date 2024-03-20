package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.role = :rolename", Role.class);
        query.setParameter("rolename", name);
        List<Role> roleList = query.getResultList();
        return roleList.isEmpty() ? Optional.empty() : Optional.of(roleList.get(0));
    }

    @Override
    public void deleteRole(Role role) {
        Role deletRole = findRoleById(role.getId());
        if (deletRole != null) {
            entityManager.remove(deletRole);
        }
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

}
