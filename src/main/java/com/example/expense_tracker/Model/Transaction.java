package com.example.expense_tracker.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private	Long	id;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private LocalDate date;
    private String note	;
    @ManyToOne
//  @JoinColumn(name = "user_id")
    private Users user;

    /* mappedBy tells JPA: “I'm not the owner.”
    You can't use mappedBy with @ManyToOne — that would be like saying,
     “I own it, but I don’t want to manage it.” */
    @ManyToOne
    private Categories category;

    public enum TransactionType {
        INCOME,
        EXPENSE
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                ", note='" + note + '\'' +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
