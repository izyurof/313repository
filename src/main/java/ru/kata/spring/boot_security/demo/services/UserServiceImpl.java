package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findUserByUsername(username);
        User user1 = user.get();
        Collection<? extends GrantedAuthority> authorities = user1.getAuthorities();
        return new org.springframework.security.core.userdetails.User(user1.getUsername(), user1.getPassword(), authorities);
    }
//        return userDao.findUserByUsername(username)
//                .map(
//                        user -> new org.springframework.security.core.userdetails.User
//                                (
//                        user.getName(),
//                        user.getPassword(),
//                        user.getAuthorities())
//                ).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не зарегистрирован", username)));
//    }
}
