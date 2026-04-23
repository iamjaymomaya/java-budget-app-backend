package com.budget_app.controllers;

import com.budget_app.requests.CategoryCreateRequest;
import com.budget_app.requests.CategoryUpdateRequest;
import com.budget_app.responses.CategoryResponse;
import com.budget_app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	public CategoryResponse create(@Valid @RequestBody CategoryCreateRequest request) {
		return categoryService.create(request);
	}

	@GetMapping
	public List<CategoryResponse> list() {
		return categoryService.list();
	}

	@GetMapping("/income")
	public List<CategoryResponse> listIncome() {
		return categoryService.listIncome();
	}

	@GetMapping("/expense")
	public List<CategoryResponse> listExpense() {
		return categoryService.listExpense();
	}

	@PutMapping("/{id}")
	public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) {
		return categoryService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		categoryService.delete(id);
	}
}
