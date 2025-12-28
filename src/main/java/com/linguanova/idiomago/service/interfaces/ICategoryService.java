package com.linguanova.idiomago.service.interfaces;

import com.linguanova.idiomago.presentation.dto.category.CategoryDTO;
import com.linguanova.idiomago.presentation.dto.category.CreateCategotyDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAll();
    CategoryDTO getById(Long categoryId);
    CategoryDTO save(CreateCategotyDTO dto);
    void delete(Long categoryId);

}
