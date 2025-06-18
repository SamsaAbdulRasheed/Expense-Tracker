package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.TransactionRequestDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.Exception.AccessDeniedException;
import com.example.expense_tracker.Mapper.TransactionMapper;
import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.CategoryRepo;
import com.example.expense_tracker.Repository.TransactionRepo;
import com.example.expense_tracker.Repository.UserRepo;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo, CategoryRepo categoryRepo, UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;

    }

    private Users getUsersByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Categories getCategoryById(Long categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private Transaction getTransactionById(Long transactionId) {
        return transactionRepo.findById(transactionId).orElseThrow(() -> new RuntimeException("transaction not found"));
    }

    @Override
    public TransactionResponseDTO saveTransaction(String username, TransactionRequestDTO requestDTO) {

        Users user = getUsersByUsername(username);
        Categories category = getCategoryById(requestDTO.getCategoryId());

        Transaction transaction = new Transaction();
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDate(requestDTO.getDate());
        transaction.setType(requestDTO.getType());
        transaction.setNote(requestDTO.getNote());
        transaction.setUser(user);
        transaction.setCategory(category);

        Transaction saved = transactionRepo.save(transaction);
        return TransactionMapper.toDto(saved);

//        return new TransactionResponseDTO(saved.getId(), saved.getAmount(), saved.getType(), saved.getDate(), saved.getNote(), saved.getCategory().getName());
    }


    @Override
    public List<TransactionResponseDTO> getAllTransactionByUser(String username) {
        Users user = getUsersByUsername(username);

//       return user.map(users->transactionRepo.findByUser(users)).orElse(Collections.emptyList());   //lambda expression
        List<Transaction> transaction = transactionRepo.findByUser(user);

       return   TransactionMapper.toDtoList(transaction);
//        return transaction.stream().map(trans -> new TransactionResponseDTO(trans.getId(), trans.getAmount(), trans.getType(), trans.getDate(), trans.getNote(), trans.getCategory().getName())).toList();


    }

    @Override
    public TransactionResponseDTO updateTransaction(String username, Long transactionId, TransactionRequestDTO requestDTO) {

        Users user = getUsersByUsername(username);
        Transaction existingTransaction = getTransactionById(transactionId);
        Categories category = getCategoryById(requestDTO.getCategoryId());

        if (!existingTransaction.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("you are not allowed to update this");
        }

        existingTransaction.setCategory(category);
        existingTransaction.setAmount(requestDTO.getAmount());
        existingTransaction.setDate(requestDTO.getDate());
        existingTransaction.setType(requestDTO.getType());

        Transaction saved = transactionRepo.save(existingTransaction);
         return TransactionMapper.toDto(saved);
//        return new TransactionResponseDTO(saved.getId(), saved.getAmount(), saved.getType(), saved.getDate(), saved.getNote(), saved.getCategory().getName());
    }


    @Override
    public void deleteTransaction(String username, Long transactionId) {

        Users user = getUsersByUsername(username);
        Transaction transaction = getTransactionById(transactionId);

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this transaction");
        }

        transactionRepo.delete(transaction);


    }

    @Override
    public List<TransactionResponseDTO> getFilteredTransactions(String username, String type, String category, LocalDate startDate, LocalDate endDate) {
        Users user = getUsersByUsername(username);

        List<Transaction> transactions = transactionRepo.findByUser(user);

        Transaction.TransactionType enumType = null;
        if (type != null && !type.isBlank()) {
            try {
                enumType = Transaction.TransactionType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Transaction Type " + type);
            }
        }

        final Transaction.TransactionType finalEnumType = enumType;
        return transactions.stream()
                .filter(t -> finalEnumType == null || t.getType() == finalEnumType)
                .filter(t -> category == null || t.getCategory().getName().equalsIgnoreCase(category))
                .filter(t -> startDate == null || !t.getDate().isBefore(startDate))
                .filter(t -> startDate == null || !t.getDate().isAfter(endDate))
                .map(TransactionMapper::toDto)
                .toList();

    }
}
