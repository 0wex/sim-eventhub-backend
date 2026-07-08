package com.cs2031.eventhub.event.domain;

import com.cs2031.eventhub.event.infrastructure.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepo;

    public EventService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<Event> search(String title, Long categoryId, String from, String to) {
        return eventRepo.search(title, categoryId, from, to);
    }

    public Event getById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }
}
