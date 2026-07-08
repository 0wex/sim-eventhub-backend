package com.cs2031.eventhub.event.application;

import com.cs2031.eventhub.event.domain.Event;
import com.cs2031.eventhub.event.domain.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        List<Map<String, Object>> result = eventService.search(title, categoryId, from, to).stream()
                .map(e -> Map.<String, Object>of(
                        "id", e.getId(),
                        "title", e.getTitle(),
                        "date", e.getDate(),
                        "location", e.getLocation(),
                        "categoryId", e.getCategory().getId()
                ))
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Event e = eventService.getById(id);
        return ResponseEntity.ok(Map.of(
                "id", e.getId(),
                "title", e.getTitle(),
                "date", e.getDate(),
                "location", e.getLocation(),
                "description", e.getDescription(),
                "categoryId", e.getCategory().getId(),
                "categoryName", e.getCategory().getName()
        ));
    }
}
