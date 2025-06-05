package com.example.expense_tracker.Controller;

import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Service.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/user")

public class UserController {

    private final UsersServiceImpl usersService;

    public UserController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user){

        Users newUser=usersService.saveUser(user);
        return ResponseEntity.ok(newUser);

    }
}
