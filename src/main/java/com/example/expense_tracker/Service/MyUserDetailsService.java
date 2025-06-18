package com.example.expense_tracker.Service;

import com.example.expense_tracker.Model.UserPrincipal;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user=  userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));


        return new UserPrincipal(user);
    }
}
