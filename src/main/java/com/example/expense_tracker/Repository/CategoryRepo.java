package com.example.expense_tracker.Repository;

import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Categories,Long> {

    List<Categories> findByUser(Users user);
    Optional<Categories> findByIdAndUser(Long id, Users user);

}
