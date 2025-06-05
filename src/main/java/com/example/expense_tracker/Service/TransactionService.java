package com.example.expense_tracker.Service;

import com.example.expense_tracker.Model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getAllTransactionByUser(String username);
}
