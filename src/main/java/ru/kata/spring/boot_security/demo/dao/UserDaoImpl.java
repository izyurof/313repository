package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private PasswordEncoder passwordEncoder;


    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        User deletUser = findUserById(user.getId());
        if (deletUser != null) {
            deletUser.getRoles().clear();
            entityManager.remove(deletUser);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.name = :username", User.class);
        query.setParameter("username", username);
        List<User> userList = query.getResultList();
        return userList.isEmpty() ? Optional.empty() : Optional.of(userList.get(0));
    }
}
