package com.example.expense_tracker.Service;

import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.CategoryRepo;
import com.example.expense_tracker.Repository.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;


    public CategoryServiceImpl(UserRepo userRepo, CategoryRepo categoryRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    public Users getUserByUsername(String username){
        return userRepo.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
    public Categories getCategoryById(Long categoryId){
        return categoryRepo.findById(categoryId)
                .orElseThrow(()->new RuntimeException("Category  not found"));
    }

    @Override
    public Categories CreateCategory(String username, Categories category) {
      Users user= getUserByUsername(username);
               category.setUser(user);
        return  categoryRepo.save(category);

    }

    @Override
    public List<Categories> getAllCategories(String username) {
        Users user= getUserByUsername(username);

        return categoryRepo.findByUser(user);
    }

    @Override
    public Categories updateCategory(String username, Long categoryId, Categories updatedCategory) {

        Users user= getUserByUsername(username);
        Categories existingCategories=  getCategoryById(categoryId);

        if(!existingCategories.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to update this category");
        }

        existingCategories.setName(existingCategories.getName());
        existingCategories.setType(updatedCategory.getType());
        return categoryRepo.save(existingCategories);
    }

    @Override
    public void deleteCategory(String username, Long categoryId) {

        Users user= getUserByUsername(username);
        Categories category=  getCategoryById(categoryId);

        if(!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not allowed to delete this category");

        }
        categoryRepo.deleteById(categoryId);

    }
}
