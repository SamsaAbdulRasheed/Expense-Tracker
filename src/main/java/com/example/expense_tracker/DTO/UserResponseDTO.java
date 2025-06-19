package com.example.expense_tracker.DTO;


import com.example.expense_tracker.Model.Transaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private List<CategoryResponseDTO> categoriesList;
    private List<TransactionResponseDTO> transactionList;



}
