package com.example.expense_tracker.Service;

import com.example.expense_tracker.DTO.CategoryRequestDTO;
import com.example.expense_tracker.DTO.CategoryResponseDTO;
import com.example.expense_tracker.DTO.TransactionResponseDTO;
import com.example.expense_tracker.Exception.AccessDeniedException;
import com.example.expense_tracker.Mapper.TransactionMapper;
import com.example.expense_tracker.Model.Categories;
import com.example.expense_tracker.Model.Transaction;
import com.example.expense_tracker.Model.Users;
import com.example.expense_tracker.Repository.CategoryRepo;
import com.example.expense_tracker.Repository.UserRepo;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

//    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);


    public CategoryServiceImpl(UserRepo userRepo, CategoryRepo categoryRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    public Users getUserByUsername(String username){
        return userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    public Categories getCategoryById(Long categoryId){
        return categoryRepo.findById(categoryId)
                .orElseThrow(()->new RuntimeException("Category  not found"));
    }

    @Override
    public CategoryResponseDTO CreateCategory(String username, CategoryRequestDTO requestDTO) {
        Users user= getUserByUsername(username);



        Categories category=new Categories();
        category.setName(requestDTO.getName());
        category.setType(requestDTO.getType());
        category.setUser(user);

        Categories saved= categoryRepo.save(category);
        return new CategoryResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getType(),
                null);

    }


    @Override
    public List<CategoryResponseDTO> getAllCategories(String username) {

        Users user= getUserByUsername(username);

        List<Categories> categories= categoryRepo.findByUser(user);

       return categories.stream()
                .map(cat -> {
                    List<TransactionResponseDTO> transaction = cat.getTransactions()
                            .stream()
                            .map(TransactionMapper::toDto)
                            .toList();
                  return  new CategoryResponseDTO(
                        cat.getId(),
                        cat.getName(),
                        cat.getType(),
                        transaction);
                }).toList();

    }

    @Override
    public CategoryResponseDTO updateCategory(String username,
                                              Long categoryId,
                                              CategoryRequestDTO updatedCategory) {

        Users user= getUserByUsername(username);
        Categories existingCategories= getCategoryById(categoryId);

        categoryRepo.findByIdAndUser(categoryId , user)
                .orElseThrow(() -> new AccessDeniedException("You are not allowed to update this category"));
//        if(!existingCategories.getUser().getId().equals(user.getId())) {
//            throw new AccessDeniedException("You are not allowed to update this category");
//        }

        existingCategories.setName(updatedCategory.getName());
        existingCategories.setType(updatedCategory.getType());

        Categories saved = categoryRepo.save(existingCategories);
//        logger.info("Category '{}' created for user '{}'", saved.getName(), user.getUsername());

        return new CategoryResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getType(),
        null);
    }

    @Override
    public void deleteCategory(String username, Long categoryId) {

        Users user= getUserByUsername(username);
        Categories category=  getCategoryById(categoryId);

        if(!category.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("You are not allowed to delete this category");

        }
        categoryRepo.deleteById(categoryId);

    }
}
