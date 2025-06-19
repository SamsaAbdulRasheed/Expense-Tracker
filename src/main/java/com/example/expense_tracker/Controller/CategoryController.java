package com.example.expense_tracker.Controller;

import com.example.expense_tracker.DTO.CategoryRequestDTO;
import com.example.expense_tracker.DTO.CategoryResponseDTO;
import com.example.expense_tracker.Service.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController()
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService= categoryService;
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(
            Principal principal,
            @RequestBody CategoryRequestDTO requestDTO){
        String username=principal.getName();
        CategoryResponseDTO dto= categoryService.CreateCategory(username,requestDTO);
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(
            Principal principal){
        String username=principal.getName();
        List<CategoryResponseDTO> categories= categoryService.getAllCategories(username);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(
            Principal principal,
            @PathVariable Long categoryId,
            @RequestBody CategoryRequestDTO updatedCategory
            ){
        String username=principal.getName();
        CategoryResponseDTO category= categoryService.updateCategory(username,categoryId,updatedCategory);
        return ResponseEntity.ok("Category updated successfully: " + category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            Principal principal ,
            @PathVariable Long categoryId){
        String username = principal.getName();
            categoryService.deleteCategory(username,categoryId);
            return ResponseEntity.ok("Category Deleted Successfully");


    }
}
