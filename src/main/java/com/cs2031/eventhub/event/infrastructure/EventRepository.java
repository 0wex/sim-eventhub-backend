package com.cs2031.eventhub.event.infrastructure;

import com.cs2031.eventhub.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("""
           SELECT e FROM Event e
           WHERE (:title IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%')))
             AND (:categoryId IS NULL OR e.category.id = :categoryId)
             AND (:from IS NULL OR e.date >= :from)
             AND (:to IS NULL OR e.date <= :to)
           """)
    List<Event> search(@Param("title") String title,
                       @Param("categoryId") Long categoryId,
                       @Param("from") String from,
                       @Param("to") String to);
}
