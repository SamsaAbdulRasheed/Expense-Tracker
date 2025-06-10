package com.example.expense_tracker.Controller;

import com.example.expense_tracker.DTO.CategoryRequestDTO;
import com.example.expense_tracker.DTO.CategoryResponseDTO;
import com.example.expense_tracker.Service.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService= categoryService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @PathVariable String username,
            @RequestBody CategoryRequestDTO requestDTO){
        CategoryResponseDTO dto= categoryService.CreateCategory(username,requestDTO);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(
            @PathVariable String username){
        List<CategoryResponseDTO> categories= categoryService.getAllCategories(username);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{username}/{categoryId}")
    public ResponseEntity<String> updateCategory(
            @PathVariable String username,
            @PathVariable Long categoryId,
            @RequestBody CategoryRequestDTO updatedCategory){
        CategoryResponseDTO category= categoryService.updateCategory(username,categoryId,updatedCategory);
        return ResponseEntity.ok("Category updated successfully: " + category);
    }

    @DeleteMapping("/{username}/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable String username,
            @PathVariable Long categoryId){
            categoryService.deleteCategory(username,categoryId);
            return ResponseEntity.ok("Category Deleted Successfully");


    }
}
