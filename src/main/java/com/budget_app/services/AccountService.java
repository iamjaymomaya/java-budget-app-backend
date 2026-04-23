package com.budget_app.services;

import com.budget_app.models.Account;
import com.budget_app.models.User;
import com.budget_app.repositories.AccountRepository;
import com.budget_app.requests.AccountCreateRequest;
import com.budget_app.requests.AccountUpdateRequest;
import com.budget_app.responses.AccountResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CurrentUserService currentUserService;

    public AccountService(AccountRepository accountRepository, CurrentUserService currentUserService) {
        this.accountRepository = accountRepository;
        this.currentUserService = currentUserService;
    }

    public AccountResponse create(AccountCreateRequest request) {
        User user = currentUserService.requireCurrentUser();

        Account account = new Account();
        account.setName(request.getName());
        account.setUser(user);

        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    public List<AccountResponse> list() {
        User user = currentUserService.requireCurrentUser();
        return accountRepository.findAllByUserIdAndDeletedAtIsNull(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AccountResponse update(Long id, AccountUpdateRequest request) {
        User user = currentUserService.requireCurrentUser();

        Account account = accountRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Account not found"));

        account.setName(request.getName());
        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    public void delete(Long id) {
        User user = currentUserService.requireCurrentUser();

        Account account = accountRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Account not found"));

        account.setDeletedAt(LocalDateTime.now());
        accountRepository.save(account);
    }

    public Account requireByIdForCurrentUser(Long id) {
        User user = currentUserService.requireCurrentUser();
        return accountRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Account not found"));
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(account.getId(), account.getName());
    }
}
