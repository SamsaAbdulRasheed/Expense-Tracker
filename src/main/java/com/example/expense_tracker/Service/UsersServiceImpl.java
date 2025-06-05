package com.example.expense_tracker.Service;

import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsersServiceImpl implements UserService {

    private final UserRepo userRepo;

    /* Constructor injection:- When Spring sees a constructor,
    it automatically gives the needed object. */
    public UsersServiceImpl(UserRepo userRepo) {
        this.userRepo=userRepo;
    }

    @Override
    public Users saveUser(Users user) {
        return userRepo.save(user);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return  userRepo.findByUsername(username);
    }
}
