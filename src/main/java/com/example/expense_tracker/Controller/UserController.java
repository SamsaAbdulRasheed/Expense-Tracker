package com.example.expense_tracker.Controller;

import com.example.expense_tracker.DTO.UserRequestDTO;
import com.example.expense_tracker.DTO.UserResponseDTO;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Service.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController()
@RequestMapping("/api")

public class UserController {

    private final UsersServiceImpl usersService;

    public UserController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO requestDTO){

        UserResponseDTO dto=usersService.registerUser(requestDTO);
        return ResponseEntity.ok(dto);

    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return usersService.verify(user);
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponseDTO> updateUser(Principal principal,@RequestBody UserRequestDTO requestDTO){
        String username = principal.getName();

        return ResponseEntity.ok(usersService.updateUser(username,requestDTO));
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUSer(Principal principal){
        String username = principal.getName();
        usersService.deleteUser(username);
        return ResponseEntity.ok("Successfully deleted user");
    }


    @GetMapping("/admin")
    public ResponseEntity<List<UserResponseDTO>> getAllUser(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }
}
