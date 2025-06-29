package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.TransactionRequestDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.DTO.TransactionSummaryDTO;
import com.example.expense_tracker.Model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    TransactionResponseDTO saveTransaction(String username,TransactionRequestDTO transaction);
    List<TransactionResponseDTO> getAllTransactionByUser(String username);
    TransactionResponseDTO updateTransaction(String username,Long TransactionId, TransactionRequestDTO dto);
    void deleteTransaction(String username,Long transactionId);
    List<TransactionResponseDTO> getFilteredTransactions(String username, String type, String category, LocalDate startDate, LocalDate endDate);

    TransactionSummaryDTO getTransactionSummary(String username);
}
