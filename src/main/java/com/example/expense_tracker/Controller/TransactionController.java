package com.example.expense_tracker.Controller;

import com.example.expense_tracker.DTO.TransactionRequestDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.Service.TransactionServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController("/api/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping()
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            Principal principal,
            @RequestBody TransactionRequestDTO requestDTO){
        String username=principal.getName();
        return ResponseEntity.ok( transactionService.saveTransaction(username,requestDTO));
    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactionByUser(
                    Principal principal){

        String username=principal.getName();

        return ResponseEntity.ok(transactionService.getAllTransactionByUser(username));

    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponseDTO>> getFilteredTransactions(
            @RequestParam(required = false)  String type,
            @RequestParam(required = false)  String category,
/*@DateTimeFormat annotation tells Spring to parse a date string from the HTTP request
into a LocalDate object using the ISO format */
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Principal principal){

        String username=principal.getName();

        return ResponseEntity.ok(transactionService.getFilteredTransactions(username,type,category,startDate,endDate));

    }


    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(
            @PathVariable String username,
            @PathVariable Long transactionId,
            @RequestBody TransactionRequestDTO requestDTO){

      return ResponseEntity.ok(transactionService.updateTransaction(username,transactionId,requestDTO));

    }

    public ResponseEntity<String> deleteTransaction(
            @PathVariable String username,
            @PathVariable Long transactionId){
        transactionService.deleteTransaction(username,transactionId);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
