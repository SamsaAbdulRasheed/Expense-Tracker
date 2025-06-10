package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.UserResponseDTO;
import com.example.expense_tracker.Model.Users;

import java.util.Optional;

public interface UserService {
    UserResponseDTO saveUser(Users user);
    Optional<Users> findByUsername(String username);

}
