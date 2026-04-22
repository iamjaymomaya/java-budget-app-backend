package com.budget_app.services;

import com.budget_app.models.Category;
import com.budget_app.models.User;
import com.budget_app.repositories.CategoryRepository;
import com.budget_app.requests.CategoryCreateRequest;
import com.budget_app.requests.CategoryUpdateRequest;
import com.budget_app.responses.CategoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final CurrentUserService currentUserService;

	public CategoryService(CategoryRepository categoryRepository, CurrentUserService currentUserService) {
		this.categoryRepository = categoryRepository;
		this.currentUserService = currentUserService;
	}

	public CategoryResponse create(CategoryCreateRequest request) {
		User user = currentUserService.requireCurrentUser();

		Category category = new Category();
		category.setName(request.getName());
		category.setType(request.getType());
		category.setColor(request.getColor());
		category.setUser(user);

		Category saved = categoryRepository.save(category);
		return toResponse(saved);
	}

	public List<CategoryResponse> list() {
		User user = currentUserService.requireCurrentUser();
		return categoryRepository.findAllByUserIdAndDeletedAtIsNull(user.getId())
				.stream()
				.map(this::toResponse)
				.toList();
	}

	public CategoryResponse update(Long id, CategoryUpdateRequest request) {
		User user = currentUserService.requireCurrentUser();

		Category category = categoryRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category not found"));

		category.setName(request.getName());
		category.setType(request.getType());
		category.setColor(request.getColor());

		Category saved = categoryRepository.save(category);
		return toResponse(saved);
	}

	public void delete(Long id) {
		User user = currentUserService.requireCurrentUser();

		Category category = categoryRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category not found"));

		category.setDeletedAt(LocalDateTime.now());
		categoryRepository.save(category);
	}

	public Category requireByIdForCurrentUser(Long id) {
		User user = currentUserService.requireCurrentUser();
		return categoryRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category not found"));
	}

	private CategoryResponse toResponse(Category category) {
		return new CategoryResponse(category.getId(), category.getName(), category.getType(), category.getColor());
	}
}
