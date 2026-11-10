package com.nour.expense_tracker.transaction;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private TransactionType type;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    // needed by Hibernate, not for our own code
    protected Transaction() {
    }

    // used by our code when creating a new transaction (no id: the database generates it)
    public Transaction(Long userId, Long categoryId, TransactionType type,
                       BigDecimal amount, String description, LocalDate date) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getCategoryId() { return categoryId; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
}