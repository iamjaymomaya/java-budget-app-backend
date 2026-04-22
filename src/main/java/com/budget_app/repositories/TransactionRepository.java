package com.budget_app.repositories;

import com.budget_app.models.Transaction;
import com.budget_app.responses.CategoryTotalResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccount_User_IdAndDeletedAtIsNullAndTransactionDateBetween(
	    Long userId,
	    LocalDate from,
	    LocalDate to
    );

    List<Transaction> findAllByAccount_User_IdAndDeletedAtIsNull(Long userId);

    Optional<Transaction> findByIdAndAccount_User_IdAndDeletedAtIsNull(Long id, Long userId);

    @Query("""
	    select new com.budget_app.responses.CategoryTotalResponse(
		t.category.id,
		t.category.name,
		t.category.type,
		sum(t.amount)
	    )
	    from Transaction t
	    where t.deletedAt is null
	      and t.account.deletedAt is null
	      and t.category is not null
	      and t.category.deletedAt is null
	      and t.account.user.id = :userId
	      and t.transactionDate between :from and :to
	    group by t.category.id, t.category.name, t.category.type
	    order by t.category.type, t.category.name
	    """)
    List<CategoryTotalResponse> totalsByCategoryBetween(
	    @Param("userId") Long userId,
	    @Param("from") LocalDate from,
	    @Param("to") LocalDate to
    );


}
