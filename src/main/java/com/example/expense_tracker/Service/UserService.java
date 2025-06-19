package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.UserRequestDTO;
import com.example.expense_tracker.DTO.UserResponseDTO;
import com.example.expense_tracker.Model.Users;

import java.util.List;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO user);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(String username, UserRequestDTO requestDTO);
    void deleteUser(String username);
    String verify(Users user);

}
