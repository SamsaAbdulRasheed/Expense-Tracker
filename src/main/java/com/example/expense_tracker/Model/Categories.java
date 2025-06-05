package com.example.expense_tracker.Model;

import jakarta.persistence.*;

import java.util.List;
import com.example.expense_tracker.Model.Transaction.TransactionType;

@Entity
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne
//    @JoinColumn(name = "user_id")
    private Users user;

  /*  @ManyToOne does not support the mappedBy attribute.
    Only @OneToMany, @OneToOne, and @ManyToMany support mappedBy. */

  /*  🔸 The owning side "maps" the relationship,
🔸 The inverse side just refers to it using mappedBy.
in this case category in the transaction entity is the owning side because it has foreign key*/

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Transaction> transactions;


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", user=" + user +
                ", transactions=" + transactions +
                '}';
    }
}
