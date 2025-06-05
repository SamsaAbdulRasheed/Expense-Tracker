package com.example.expense_tracker.Service;

import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.TransactionRepo;
import com.example.expense_tracker.Repository.UserRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo,UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.userRepo=userRepo;

    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public List<Transaction> getAllTransactionByUser(String username) {
        Optional<Users> user= userRepo.findByUsername(username);

//       return user.map(users->transactionRepo.findByUser(users)).orElse(Collections.emptyList());   //lambda expression
        return user.map(transactionRepo::findByUser).orElse(Collections.emptyList());  //Methode Reference

    }
}
