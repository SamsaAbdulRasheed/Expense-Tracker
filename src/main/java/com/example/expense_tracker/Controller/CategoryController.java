package com.example.expense_tracker.Controller;

import com.example.expense_tracker.Model.Categories;
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
    public ResponseEntity<Categories> createCategory(@PathVariable String username,
                                         @RequestBody Categories category){
        Categories createdCategory= categoryService.CreateCategory(username,category);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Categories>> getAllCategories(@PathVariable String username){
        List<Categories> categories= categoryService.getAllCategories(username);
       return ResponseEntity.ok(categories);
    }

    @PutMapping("/{username}/{categoryId}")
    public ResponseEntity<Categories> updateCategory(@PathVariable String username,
                                                     @PathVariable Long categoryId,
                                                     @RequestBody Categories updatedCategory){
        Categories category= categoryService.updateCategory(username,categoryId,updatedCategory);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{username}/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable String username, @PathVariable Long categoryId){
        try {
            categoryService.deleteCategory(username,categoryId);
            return ResponseEntity.ok("deleted Successfully");

        } catch (Exception e) {
            return  ResponseEntity.status(500).body("Deletion failed");
        }
    }
}
