package com.cs2031.eventhub.event.application;

import com.cs2031.eventhub.event.domain.Event;
import com.cs2031.eventhub.event.domain.EventService;
import com.cs2031.eventhub.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Eventos", description = "Búsqueda de eventos con filtros y detalle por ID (público).")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    record EventResponse(
            @Schema(description = "Identificador único del evento", example = "1") Long id,
            @Schema(description = "Título del evento", example = "Concierto de la Orquesta Sinfónica Municipal") String title,
            @Schema(description = "Fecha de realización (YYYY-MM-DD)", example = "2026-08-08") String date,
            @Schema(description = "Lugar donde se realiza", example = "Teatro Municipal") String location,
            @Schema(description = "ID de la categoría a la que pertenece", example = "1") Long categoryId) {}

    record EventDetailResponse(
            @Schema(description = "Identificador único del evento", example = "3") Long id,
            @Schema(description = "Título del evento", example = "Noche de Jazz y Bolero") String title,
            @Schema(description = "Fecha de realización (YYYY-MM-DD)", example = "2026-08-22") String date,
            @Schema(description = "Lugar donde se realiza", example = "Plaza de Armas") String location,
            @Schema(description = "Descripción del evento", example = "Velada al aire libre con ensambles de jazz y trío de boleros.") String description,
            @Schema(description = "ID de la categoría", example = "1") Long categoryId,
            @Schema(description = "Nombre de la categoría", example = "Música") String categoryName) {}

    @Operation(summary = "Buscar eventos con filtros",
            description = "Todos los filtros son opcionales y combinables. Sin parámetros devuelve los 40 eventos.")
    @ApiResponse(responseCode = "200", description = "Lista de eventos que cumplen los filtros")
    @GetMapping("/search")
    public ResponseEntity<List<EventResponse>> search(
            @Parameter(description = "Busca por título (contiene, sin distinguir mayúsculas)", example = "taller")
            @RequestParam(required = false) String title,
            @Parameter(description = "Filtra por categoría", example = "1")
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Fecha mínima de realización, YYYY-MM-DD (inclusive)", example = "2026-08-10")
            @RequestParam(required = false) String from,
            @Parameter(description = "Fecha máxima de realización, YYYY-MM-DD (inclusive)", example = "2026-08-20")
            @RequestParam(required = false) String to) {
        List<EventResponse> result = eventService.search(title, categoryId, from, to).stream()
                .map(e -> new EventResponse(e.getId(), e.getTitle(), e.getDate(),
                        e.getLocation(), e.getCategory().getId()))
                .toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Detalle de un evento")
    @ApiResponse(responseCode = "200", description = "Evento encontrado")
    @ApiResponse(responseCode = "400", description = "El evento no existe",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<EventDetailResponse> getById(
            @Parameter(description = "ID del evento", example = "3") @PathVariable Long id) {
        Event e = eventService.getById(id);
        return ResponseEntity.ok(new EventDetailResponse(e.getId(), e.getTitle(), e.getDate(),
                e.getLocation(), e.getDescription(), e.getCategory().getId(), e.getCategory().getName()));
    }
}
