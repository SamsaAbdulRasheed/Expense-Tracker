package com.example.expense_tracker.Service;

import com.example.expense_tracker.Model.Categories;

import java.util.List;

public interface CategoryService {

     Categories CreateCategory(String username, Categories category);
     List<Categories> getAllCategories(String username);
     Categories updateCategory(String useranme,Long categoryId, Categories updatedCategory);
     void deleteCategory(String username,Long categoryId);
}
