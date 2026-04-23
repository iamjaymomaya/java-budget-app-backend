package com.budget_app.services;

import com.budget_app.models.Account;
import com.budget_app.models.Category;
import com.budget_app.models.Transaction;
import com.budget_app.models.User;
import com.budget_app.repositories.TransactionRepository;
import com.budget_app.requests.TransactionCreateRequest;
import com.budget_app.responses.TransactionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;
	private final CurrentUserService currentUserService;
	private final AccountService accountService;
	private final CategoryService categoryService;

	public TransactionService(
			TransactionRepository transactionRepository,
			CurrentUserService currentUserService,
			AccountService accountService,
			CategoryService categoryService
	) {
		this.transactionRepository = transactionRepository;
		this.currentUserService = currentUserService;
		this.accountService = accountService;
		this.categoryService = categoryService;
	}

	public TransactionResponse create(TransactionCreateRequest request) {
		currentUserService.requireCurrentUser();

		Account account = accountService.requireByIdForCurrentUser(request.getAccountId());
		Category category = categoryService.requireByIdForCurrentUser(request.getCategoryId());

		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setCategory(category);
		tx.setAmount(request.getAmount());
		tx.setTransactionType(request.getTransactionType());
		tx.setTransactionDate(request.getTransactionDate());
		tx.setDescription(request.getDescription());

		Transaction saved = transactionRepository.save(tx);
		return toResponse(saved);
	}

	public List<TransactionResponse> list(LocalDate from, LocalDate to) {
		User user = currentUserService.requireCurrentUser();

		if (from != null && to != null && from.isAfter(to)) {
			throw new ResponseStatusException(BAD_REQUEST, "'from' must be on or before 'to'");
		}

		List<Transaction> transactions;
		if (from != null && to != null) {
			transactions = transactionRepository
					.findAllByAccount_User_IdAndDeletedAtIsNullAndTransactionDateBetween(user.getId(), from, to);
		} else if (from != null) {
			transactions = transactionRepository
					.findAllByAccount_User_IdAndDeletedAtIsNullAndTransactionDateGreaterThanEqual(user.getId(), from);
		} else if (to != null) {
			transactions = transactionRepository
					.findAllByAccount_User_IdAndDeletedAtIsNullAndTransactionDateLessThanEqual(user.getId(), to);
		} else {
			transactions = transactionRepository.findAllByAccount_User_IdAndDeletedAtIsNull(user.getId());
		}

		return transactions.stream().map(this::toResponse).toList();
	}

	public void delete(Long id) {
		User user = currentUserService.requireCurrentUser();

		Transaction tx = transactionRepository.findByIdAndAccount_User_IdAndDeletedAtIsNull(id, user.getId())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Transaction not found"));

		tx.setDeletedAt(LocalDateTime.now());
		transactionRepository.save(tx);
	}

	private TransactionResponse toResponse(Transaction tx) {
		Long categoryId = tx.getCategory() != null ? tx.getCategory().getId() : null;
		String categoryName = tx.getCategory() != null ? tx.getCategory().getName() : null;
		var categoryType = tx.getCategory() != null ? tx.getCategory().getType() : null;

		return new TransactionResponse(
				tx.getId(),
				tx.getAmount(),
				tx.getTransactionType(),
				tx.getTransactionDate(),
				tx.getDescription(),
				tx.getAccount().getId(),
				tx.getAccount().getName(),
				categoryId,
				categoryName,
				categoryType
		);
	}
}
