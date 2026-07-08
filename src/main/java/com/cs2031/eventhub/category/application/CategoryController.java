package com.cs2031.eventhub.category.application;

import com.cs2031.eventhub.category.domain.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getCategories() {
        List<Map<String, Object>> result = categoryService.findAll().stream()
                .map(c -> Map.<String, Object>of(
                        "id", c.getId(),
                        "name", c.getName(),
                        "description", c.getDescription(),
                        "imageUrl", c.getImageUrl()
                ))
                .toList();
        return ResponseEntity.ok(result);
    }
}
