package com.onlineexaminationportal.repository;

import com.onlineexaminationportal.entity.exam.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
