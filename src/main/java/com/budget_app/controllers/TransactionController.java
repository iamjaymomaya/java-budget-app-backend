package com.budget_app.controllers;

import com.budget_app.requests.TransactionCreateRequest;
import com.budget_app.responses.TransactionResponse;
import com.budget_app.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	public TransactionResponse create(@Valid @RequestBody TransactionCreateRequest request) {
		return transactionService.create(request);
	}

	@GetMapping
	public List<TransactionResponse> list(
			@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
	) {
		return transactionService.list(from, to);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		transactionService.delete(id);
	}
}
