package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.TransactionRequestDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.DTO.TransactionSummaryDTO;
import com.example.expense_tracker.Exception.AccessDeniedException;
import com.example.expense_tracker.Mapper.TransactionMapper;
import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.CategoryRepo;
import com.example.expense_tracker.Repository.TransactionRepo;
import com.example.expense_tracker.Repository.UserRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Categories getCategoryById(Long categoryId) {
        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private Transaction getTransactionById(Long transactionId) {
        return transactionRepo.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("transaction not found"));
    }

    @Override
    public TransactionResponseDTO saveTransaction(
            String username, TransactionRequestDTO requestDTO) {

        Users user = getUsersByUsername(username);
        Categories category= categoryRepo.findByIdAndUser(requestDTO.getCategoryId(),user)
                .orElseThrow(() -> new IllegalArgumentException("Category not found for user"));


//        Categories category = getCategoryById(requestDTO.getCategoryId());

        Transaction transaction= TransactionMapper.toEntity(requestDTO, category, user);

        Transaction saved = transactionRepo.save(transaction);
        return TransactionMapper.toDto(saved);

//        return new TransactionResponseDTO(saved.getId(), saved.getAmount(), saved.getType(), saved.getDate(), saved.getNote(), saved.getCategory().getName());
    }


    @Override
    public List<TransactionResponseDTO> getAllTransactionByUser(String username) {
        Users user = getUsersByUsername(username);

//       return user.map(users->transactionRepo.findByUser(users)).orElse(Collections.emptyList());   //lambda expression
        List<Transaction> transaction = transactionRepo.findByUser(user);

       return TransactionMapper.toDtoList(transaction);
//        return transaction.stream().map(trans -> new TransactionResponseDTO(trans.getId(), trans.getAmount(), trans.getType(), trans.getDate(), trans.getNote(), trans.getCategory().getName())).toList();


    }

    @Override
    public TransactionResponseDTO updateTransaction(
            String username, Long transactionId, TransactionRequestDTO requestDTO) {

        Users user = getUsersByUsername(username);
        Transaction existingTransaction = getTransactionById(transactionId);
        Categories category = getCategoryById(requestDTO.getCategoryId());

        if (!existingTransaction
                .getUser()
                .getId()
                .equals(user.getId())) {
            throw new AccessDeniedException("you are not allowed to update this");
        }

        existingTransaction.setCategory(category);
        existingTransaction.setAmount(requestDTO.getAmount());
        existingTransaction.setDate(requestDTO.getDate());
        existingTransaction.setType(category.getType());


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
    public List<TransactionResponseDTO> getFilteredTransactions(
            String username, String type, String category, LocalDate startDate, LocalDate endDate) {
        Users user = getUsersByUsername(username);

        boolean noFilter =
                category == null || category.isBlank() &&
                type == null || type.isBlank() &&
                startDate == null &&
                endDate == null;

        if(noFilter){
            return transactionRepo.findTop10ByUserOrderByDateDesc(user).stream()
                    .map(TransactionMapper::toDto)
                                .toList();
        }

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
                .filter(t -> category == null   || category.isBlank() || t.getCategory().getName().equalsIgnoreCase(category))
                .filter(t -> startDate == null || !t.getDate().isBefore(startDate))
                .filter(t -> endDate == null || !t.getDate().isAfter(endDate))
                .map(TransactionMapper::toDto)
                .toList();

    }

    @Override

    public TransactionSummaryDTO getTransactionSummary(String username) {
        Users user = getUsersByUsername(username);
        List<Transaction> transactions = transactionRepo.findByUser(user);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        Map<String, BigDecimal> monthlySummary = new HashMap<>();

        for (Transaction transaction : transactions) {
            BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
            String key = transaction.getDate().getYear() + "-" + String.format("%02d", transaction.getDate().getMonthValue());

            // Monthly summary
            monthlySummary.put(key, monthlySummary.getOrDefault(key, BigDecimal.ZERO).add(amount));

            // Type summary
            if (transaction.getType() == Transaction.TransactionType.INCOME) {
                totalIncome = totalIncome.add(amount);
            } else if (transaction.getType() == Transaction.TransactionType.EXPENSE) {
                totalExpense = totalExpense.add(amount);
            }
        }

        BigDecimal netBalance = totalIncome.subtract(totalExpense);
        if(netBalance.compareTo(BigDecimal.ZERO)<0){
            netBalance=BigDecimal.ZERO;

        }

        return new TransactionSummaryDTO(totalIncome, totalExpense, netBalance, monthlySummary);
    }

}
