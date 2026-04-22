package com.budget_app.controllers;

import com.budget_app.requests.AccountCreateRequest;
import com.budget_app.requests.AccountUpdateRequest;
import com.budget_app.responses.AccountResponse;
import com.budget_app.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponse create(@Valid @RequestBody AccountCreateRequest request) {
        return accountService.create(request);
    }

    @GetMapping
    public List<AccountResponse> list() {
        return accountService.list();
    }

    @PutMapping("/{id}")
    public AccountResponse update(@PathVariable Long id, @Valid @RequestBody AccountUpdateRequest request) {
        return accountService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }
}
