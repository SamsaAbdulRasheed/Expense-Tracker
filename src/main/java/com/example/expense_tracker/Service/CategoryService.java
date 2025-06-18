package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.CategoryRequestDTO;
import com.example.expense_tracker.DTO.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {


     CategoryResponseDTO CreateCategory(String username, CategoryRequestDTO requestDTO);

     List<CategoryResponseDTO> getAllCategories(String username);
     CategoryResponseDTO updateCategory(String username,Long categoryId, CategoryRequestDTO updatedCategory);
     void deleteCategory(String username,Long categoryId);
}
