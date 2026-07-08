package com.cs2031.eventhub.category.infrastructure;

import com.cs2031.eventhub.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
