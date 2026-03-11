package com.budget_app.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime transactionDate;

    private LocalDateTime deletedAt;

    private String description;
}
