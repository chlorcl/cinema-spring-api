package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.user.Role;
import me.chlorcl.cinemaapi.model.user.User;
import me.chlorcl.cinemaapi.repository.UserRepository;
import me.chlorcl.cinemaapi.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthResolver {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    public AuthResolver(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @MutationMapping
    public Map<String, Object> login(@Argument LoginInput input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        User user = (User) authentication.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("email", user.getEmail());
        userMap.put("name", user.getName());
        userMap.put("surname", user.getSurname());
        userMap.put("phone", user.getPhone());
        userMap.put("role", user.getRole());
        
        response.put("user", userMap);
        
        return response;
    }

    @MutationMapping
    public Map<String, Object> register(@Argument RegisterInput input) {
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        // Create new user
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail(input.getEmail());
        user.setName(input.getName());
        user.setSurname(input.getSurname());
        user.setPhone(input.getPhone());
        user.setRole(Role.ROLE_USER); // Default role
        user.setEnabled(true);
        user.setVerified(true); // For simplicity, we're setting verified to true

        userRepository.save(user);

        // Authenticate the user after registration
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("email", user.getEmail());
        userMap.put("name", user.getName());
        userMap.put("surname", user.getSurname());
        userMap.put("phone", user.getPhone());
        userMap.put("role", user.getRole());
        
        response.put("user", userMap);
        
        return response;
    }

    public static class LoginInput {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class RegisterInput {
        private String username;
        private String password;
        private String email;
        private String name;
        private String surname;
        private String phone;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
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
    }
}