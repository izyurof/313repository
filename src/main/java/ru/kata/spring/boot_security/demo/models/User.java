package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Поле с именем не должно быть пустым")
    @Size(
            min = 2,
            max = 64,
            message = "Имя должно содержать не менее 2-х символов и не более 64")
    @Pattern(
            regexp = "^[A-Za-zА-ЯЁа-яё]+([- ][A-Za-zА-ЯЁа-яё]+)*$",
            message = "Можно использовать русские и английские буквы, а также символы пробела и дефис"
    )
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Поле с фамилией не должно быть пустым")
    @Size(
            min = 2,
            max = 64,
            message = "Имя должно содержать не менее 2-х символов и не более 64"
    )
    @Pattern(
            regexp = "^[A-Za-zА-ЯЁа-яё]+([- ][A-Za-zА-ЯЁа-яё]+)*$",
            message = "Можно использовать русские и английские буквы, а также символы пробела и дефис"
    )
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Поле с возрастом не должно быть пустым")
    @Min(value = 10, message = "Возраст не может быть меньше 10")
    @Max(value = 100, message = "Возраст не может быть больше 100")
    @Column(name = "age")
    private Byte age;

    @NotBlank(message = "Поле с электронной почтой не должно быть пустым")
    @Pattern(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Поле с паролем не должно быть пустым")
    @Size(min = 4, max = 64, message = "Пароль должен содержать не менее 4 символов")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
    }

    public User(String name, String lastName, Byte age, String email) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
            roles.add(role);
        }
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(age, user.age) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, age, email);
    }
}
