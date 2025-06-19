package com.example.expense_tracker.Mapper;

import com.example.expense_tracker.DTO.TransactionRequestDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;

import java.util.List;

public class TransactionMapper {
    public static TransactionResponseDTO toDto(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setDate(transaction.getDate());

        if (transaction.getCategory() != null) {
            dto.setCategoryName(transaction.getCategory().getName());
        }

        return dto;
    }

    public static List<TransactionResponseDTO> toDtoList(List<Transaction> transaction){

       return transaction.stream()
                .map(TransactionMapper::toDto)
               .toList();


    }

    public static Transaction toEntity(TransactionRequestDTO requestDTO , Categories category , Users user) {
        Transaction transaction = new Transaction();
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDate(requestDTO.getDate());
        transaction.setType(category.getType());
        transaction.setNote(requestDTO.getNote());
        transaction.setUser(user);
        transaction.setCategory(category);
return transaction;

    }
}
