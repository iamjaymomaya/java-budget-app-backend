package com.budget_app.controllers;

import com.budget_app.responses.CategoryTotalResponse;
import com.budget_app.responses.TransactionResponse;
import com.budget_app.services.ReportService;
import com.budget_app.services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

	private final ReportService reportService;
	private final TransactionService transactionService;

	public ReportController(ReportService reportService, TransactionService transactionService) {
		this.reportService = reportService;
		this.transactionService = transactionService;
	}

	@GetMapping("/transactions")
	public List<TransactionResponse> transactions(
			@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
	) {
		return transactionService.list(from, to);
	}

	@GetMapping("/monthly")
	public List<CategoryTotalResponse> monthly(
			@RequestParam int year,
			@RequestParam int month
	) {
		return reportService.monthlyTotals(year, month);
	}

	@GetMapping("/yearly")
	public List<CategoryTotalResponse> yearly(
			@RequestParam int year
	) {
		return reportService.yearlyTotals(year);
	}
}

