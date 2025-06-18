package com.example.expense_tracker.DTO;

import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
//@AllArgsConstructor
public class TransactionResponseDTO {

    private Long id;
    private Double amount;
    private Transaction.TransactionType type;
    private LocalDate date;
    private String note	;
    private String categoryName;

}
