package ru.kata.spring.boot_security.demo.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, PasswordEncoder passwordEncoder, UserService userService) {
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .permitAll();
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @PostConstruct
    public void loadTestUser() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        User user = new User("user", "userov", (byte) 25, "user@gmail.com");
        User admin = new User("admin", "adminov", (byte) 25, "admin@gmail.com");
        user.setPassword("password");
        user.addRole(roleUser);
        admin.setPassword("password");
        admin.addRole(roleAdmin);
        userService.saveUser(user);
        userService.saveUser(admin);
        user.setRoles(Set.of(roleUser));
        admin.setRoles(Set.of(roleAdmin));
    }
}