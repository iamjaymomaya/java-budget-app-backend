package com.budget_app.repositories;

import com.budget_app.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUserIdAndDeletedAtIsNull(Long userId);

    Optional<Account> findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);
}
