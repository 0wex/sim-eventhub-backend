package com.cs2031.eventhub.user.application;

import com.cs2031.eventhub.auth.domain.AuthService;
import com.cs2031.eventhub.user.domain.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    record RegisterRequest(String email, String password, String name) {}
    record AgendaRequest(Long eventId) {}

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest req) {
        String token = authService.register(req.email(), req.password(), req.name());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/agenda")
    public ResponseEntity<Void> addAgenda(@AuthenticationPrincipal UserDetails user,
                                          @RequestBody AgendaRequest req) {
        userService.addAgenda(user.getUsername(), req.eventId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/agenda")
    public ResponseEntity<Void> removeAgenda(@AuthenticationPrincipal UserDetails user,
                                             @RequestBody AgendaRequest req) {
        userService.removeAgenda(user.getUsername(), req.eventId());
        return ResponseEntity.ok().build();
    }
}
