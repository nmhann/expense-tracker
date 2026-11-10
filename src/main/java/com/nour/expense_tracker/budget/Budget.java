package com.nour.expense_tracker.budget;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "category_id", "month"}))
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    // first day of the month, e.g. 2026-11-01 means "November 2026"
    @Column(name = "month", nullable = false)
    private LocalDate month;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    // needed by Hibernate
    protected Budget() {
    }

    // used by our code when creating a new budget (no id: the database generates it)
    public Budget(Long userId, Long categoryId, LocalDate month, BigDecimal amount) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.month = month;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getCategoryId() { return categoryId; }
    public LocalDate getMonth() { return month; }
    public BigDecimal getAmount() { return amount; }
}