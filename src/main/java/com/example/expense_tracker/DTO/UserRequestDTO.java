package com.example.expense_tracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private String role;

}
