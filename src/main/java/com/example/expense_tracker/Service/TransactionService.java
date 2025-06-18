package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.TransactionRequestDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.Model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    TransactionResponseDTO saveTransaction(String username,TransactionRequestDTO transaction);
    List<TransactionResponseDTO> getAllTransactionByUser(String username);
    TransactionResponseDTO updateTransaction(String username,Long TransactionId, TransactionRequestDTO dto);
    void deleteTransaction(String username,Long transactionId);
    List<TransactionResponseDTO> getFilteredTransactions(String username, String type, String category, LocalDate startDate, LocalDate endDate);
}
