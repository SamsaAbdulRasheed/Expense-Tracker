package com.example.expense_tracker.Mapper;

import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.Model.Transaction;

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

}
