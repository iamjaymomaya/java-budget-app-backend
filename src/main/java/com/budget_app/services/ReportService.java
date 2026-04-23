package com.budget_app.services;

import com.budget_app.models.User;
import com.budget_app.repositories.TransactionRepository;
import com.budget_app.responses.CategoryTotalResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReportService {

	private final TransactionRepository transactionRepository;
	private final CurrentUserService currentUserService;

	public ReportService(TransactionRepository transactionRepository, CurrentUserService currentUserService) {
		this.transactionRepository = transactionRepository;
		this.currentUserService = currentUserService;
	}

	public List<CategoryTotalResponse> monthlyTotals(int year, int month) {
		if (year < 1) {
			throw new ResponseStatusException(BAD_REQUEST, "Invalid year");
		}
		if (month < 1 || month > 12) {
			throw new ResponseStatusException(BAD_REQUEST, "Invalid month (must be 1-12)");
		}

		User user = currentUserService.requireCurrentUser();
		YearMonth ym = YearMonth.of(year, month);
		LocalDate from = ym.atDay(1);
		LocalDate to = ym.atEndOfMonth();
		return transactionRepository.totalsByCategoryBetween(user.getId(), from, to);
	}

	public List<CategoryTotalResponse> yearlyTotals(int year) {
		if (year < 1) {
			throw new ResponseStatusException(BAD_REQUEST, "Invalid year");
		}

		User user = currentUserService.requireCurrentUser();
		LocalDate from = LocalDate.of(year, 1, 1);
		LocalDate to = LocalDate.of(year, 12, 31);
		return transactionRepository.totalsByCategoryBetween(user.getId(), from, to);
	}
}

