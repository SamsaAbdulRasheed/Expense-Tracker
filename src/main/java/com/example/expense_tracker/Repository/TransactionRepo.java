package com.example.expense_tracker.Repository;

import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUser(Users user);
}
