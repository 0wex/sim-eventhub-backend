package com.cs2031.eventhub.event.domain;

import com.cs2031.eventhub.category.domain.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(name = "event_date")
    private String date;
    private String location;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Event() {}

    public Event(String title, String date, String location, String description, Category category) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
}
