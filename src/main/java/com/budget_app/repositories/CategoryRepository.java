package com.budget_app.repositories;

import com.budget_app.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByUserIdAndDeletedAtIsNull(Long userId);

	Optional<Category> findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

}
