package com.onlineexaminationportal.service;

import com.onlineexaminationportal.dto.CategoryDto;
import com.onlineexaminationportal.entity.exam.Category;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface CategoryService {

    public CategoryDto addCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto);

    public Set<Category> getCategories();

    public CategoryDto getCategory(Long categoryId);

    public void deleteCategory(Long categoryId);

    CategoryDto mapToDto(Category category);

    Category mapToEntity(CategoryDto categoryDto);
}
