package com.example.expense_tracker.DTO;


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
//    private List<Transaction> transactionList;



}
