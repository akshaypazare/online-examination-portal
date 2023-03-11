package com.onlineexaminationportal.service.Impl;

import com.onlineexaminationportal.dto.CategoryDto;
import com.onlineexaminationportal.dto.UserDto;
import com.onlineexaminationportal.entity.User;
import com.onlineexaminationportal.entity.exam.Category;
import com.onlineexaminationportal.repository.CategoryRepository;
import com.onlineexaminationportal.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    private ModelMapper mapper;

    public CategoryServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        Category addCategory = categoryRepository.save(category);

        return mapToDto(addCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category updatedCategory = categoryRepository.save(category);

        return mapToDto(updatedCategory);
    }

    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(categoryRepository.findAll()); //convert all the category object into set using typecast
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).get();

        return mapToDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);

    }

    @Override
    public CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public Category mapToEntity(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        return category;
    }
}
