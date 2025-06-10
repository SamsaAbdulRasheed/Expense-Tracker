package com.example.expense_tracker.DTO;

import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    private String name;
    private Transaction.TransactionType type;
//    private Users user;

}
