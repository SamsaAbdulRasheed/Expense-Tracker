package com.example.expense_tracker.DTO;

import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Transaction;

import com.example.expense_tracker.Model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private Transaction.TransactionType type;
//  private UserResponseDTO user;
    private List<TransactionResponseDTO> transaction;

    @Override
    public String toString() {
        return "CategoryResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", transaction=" + transaction +
                '}';
    }
}
