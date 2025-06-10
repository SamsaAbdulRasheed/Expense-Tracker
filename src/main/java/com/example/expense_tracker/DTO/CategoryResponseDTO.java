package com.example.expense_tracker.DTO;

import com.example.expense_tracker.Model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.catalina.User;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {

private Long id;
    private String name;
    private Transaction.TransactionType type;

}
