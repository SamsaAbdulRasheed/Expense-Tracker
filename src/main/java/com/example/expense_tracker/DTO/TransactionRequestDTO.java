package com.example.expense_tracker.DTO;

import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class TransactionRequestDTO {

    private Double amount;
    private Transaction.TransactionType type;
    private LocalDate date;
    private String note	;
    private Long categoryId;



}
