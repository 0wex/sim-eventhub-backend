package com.cs2031.eventhub.user.domain;

import com.cs2031.eventhub.event.infrastructure.EventRepository;
import com.cs2031.eventhub.user.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepo, EventRepository eventRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public User register(String email, String password, String name) {
        if (userRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }
        return userRepo.save(new User(email, encoder.encode(password), name));
    }

    public void addAgenda(String email, Long eventId) {
        User user = userRepo.findByEmail(email).orElseThrow();
        var event = eventRepo.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        user.getAgenda().add(event);
        userRepo.save(user);
    }

    public void removeAgenda(String email, Long eventId) {
        User user = userRepo.findByEmail(email).orElseThrow();
        user.getAgenda().removeIf(e -> e.getId().equals(eventId));
        userRepo.save(user);
    }
}
