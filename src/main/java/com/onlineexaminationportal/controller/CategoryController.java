package com.onlineexaminationportal.controller;

import com.onlineexaminationportal.dto.CategoryDto;
import com.onlineexaminationportal.entity.exam.Category;
import com.onlineexaminationportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/Category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //To add Category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto addCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(addCategory, HttpStatus.CREATED);
    }

    //To get category by id
    @GetMapping("/Get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long categoryId){
        CategoryDto getCategoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(getCategoryDto);
    }

    //To get all the category
    @GetMapping("/GetAll")
    public ResponseEntity<?> getCategories(){
        Set<Category> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //To update category
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/Update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto);
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }

    //To delete a Category by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category Deleted Successfully.", HttpStatus.OK);
    }
}
