package com.cs2031.eventhub.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(
        @Schema(description = "Mensaje de error legible", example = "Event not found")
        String error) {}
