package com.id.akn.service;

import com.id.akn.exception.CategoryException;
import com.id.akn.model.Category;
import com.id.akn.request.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryDTOById(Byte id) throws CategoryException;
    Category getCategoryById(Byte id) throws CategoryException;
    CategoryDTO createCastegory(CategoryDTO categoryDTO) throws CategoryException;
    CategoryDTO updateCategory(Byte id, CategoryDTO categoryDTO) throws CategoryException;
    void deleteCategory(Byte id) throws CategoryException;
    CategoryDTO convertToDTO(Category category);
}CategoryDTO createCastegory(CategoryDTO categoryDTO) throws CategoryException;
    CategoryDTO updateCategory(Byte id, CategoryDTO categoryDTO) throws CategoryException;
    void deleteCategory(Byte id) throws CategoryException;
    CategoryDTO conv
