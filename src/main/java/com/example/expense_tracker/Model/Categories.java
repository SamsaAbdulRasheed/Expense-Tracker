package com.example.expense_tracker.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import com.example.expense_tracker.Model.Transaction.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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




    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", userId=" + (user != null ? user.getId() : null) +
                ", transactions=" + transactions +
                '}';
    }
}
