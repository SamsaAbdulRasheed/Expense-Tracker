package com.example.expense_tracker.DTO;

import com.example.expense_tracker.Model.Transaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    private String name;
    private Transaction.TransactionType type;
//    private Users user;

}
