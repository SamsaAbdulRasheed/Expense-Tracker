package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.CategoryResponseDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.DTO.UserRequestDTO;
import com.example.expense_tracker.DTO.UserResponseDTO;
import com.example.expense_tracker.Exception.AccessDeniedException;
import com.example.expense_tracker.Mapper.TransactionMapper;
import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder encode = new BCryptPasswordEncoder(12);

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    /* Constructor injection:- When Spring sees a constructor,
    it automatically gives the needed object. */
    public UsersServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO requestDTO) {
        Users user = new Users();
        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail());
        user.setRole(requestDTO.getRole());

        user.setPassword(encode.encode(requestDTO.getPassword()));

        if (userRepo.existsByUsername(user.getUsername())) {
            throw new AccessDeniedException("Username already exists");
        }

        if (userRepo.existsByEmail(user.getEmail())) {
            throw new AccessDeniedException("Email already in use");
        }
        Users saved = userRepo.save(user);


        return new UserResponseDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getPassword(),
                saved.getRole(),
                null,
                null
                );
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<Users> user = userRepo.findAll();

        return user
                .stream()
                .map(ls -> {
                    List<CategoryResponseDTO> category = ls.getCategories()
                            .stream()
                            .map(cat ->
                                     new CategoryResponseDTO(
                                            cat.getId(),
                                            cat.getName(),
                                            cat.getType(),
                                             null))
                            .toList();

                    List<TransactionResponseDTO> transaction=ls.getTransaction()
                            .stream()
                            .map(TransactionMapper::toDto)
                            .toList();

                    return new UserResponseDTO(
                               ls.getId(),
                               ls.getUsername(),
                               ls.getEmail(),
                               ls.getPassword(),
                               ls.getRole(),
                               category,
                            transaction);

                }).toList();




//        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), category);
    }

    @Override
    public UserResponseDTO updateUser(String username, UserRequestDTO requestDTO) {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail());

        Users saved = userRepo.save(user);
        List<CategoryResponseDTO> category = saved.getCategories().stream()
                .map(cat -> new CategoryResponseDTO(
                        cat.getId(),
                        cat.getName(),
                        cat.getType(),
                        null ))
                .toList();


        return new UserResponseDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getPassword(),
                saved.getRole(),
                category,
                null);

    }

    @Override
    public void deleteUser(String username) {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepo.delete(user);
    }

    @Override
    public String verify(Users user) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }

        return "fail";
    }
}
