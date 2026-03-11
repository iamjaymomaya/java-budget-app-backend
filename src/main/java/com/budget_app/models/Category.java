package com.budget_app.models;

import com.budget_app.enums.CategoryType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String color;

    private LocalDateTime deletedAt;
}
