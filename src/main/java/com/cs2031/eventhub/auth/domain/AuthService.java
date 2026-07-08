package com.cs2031.eventhub.auth.domain;

import com.cs2031.eventhub.config.JwtUtil;
import com.cs2031.eventhub.user.domain.User;
import com.cs2031.eventhub.user.domain.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(String email, String password, String name) {
        User user = userService.register(email, password, name);
        return jwtUtil.generate(user.getEmail());
    }

    public String login(String email, String password) {
        User user = (User) userService.loadUserByUsername(email);
        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return jwtUtil.generate(email);
    }
}
