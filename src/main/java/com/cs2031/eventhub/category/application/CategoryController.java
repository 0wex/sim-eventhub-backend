package com.cs2031.eventhub.category.application;

import com.cs2031.eventhub.category.domain.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categorías", description = "Categorías culturales de la agenda municipal (público).")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    record CategoryResponse(
            @Schema(description = "Identificador único de la categoría", example = "1") Long id,
            @Schema(description = "Nombre de la categoría", example = "Música") String name,
            @Schema(description = "Descripción de la categoría", example = "Conciertos y recitales en espacios públicos") String description,
            @Schema(description = "URL de la imagen de la categoría", example = "https://via.placeholder.com/300") String imageUrl) {}

    @Operation(summary = "Listar todas las categorías",
            description = "Devuelve las 8 categorías culturales precargadas.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> result = categoryService.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getDescription(), c.getImageUrl()))
                .toList();
        return ResponseEntity.ok(result);
    }
}
