package me.chlorcl.cinemaapi.model.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private boolean isVerified;
    private boolean isEnabled;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public static User withDefaultPasswordEncoder(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("{noop}" + password);
        user.setEnabled(true);
        user.setRole(Role.ROLE_USER);
        user.setVerified(true);
        return user;
    }

    public static User withDefaultPasswordEncoder(String username, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("{noop}" + password);
        user.setEnabled(true);
        user.setRole(role);
        user.setVerified(true);
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
