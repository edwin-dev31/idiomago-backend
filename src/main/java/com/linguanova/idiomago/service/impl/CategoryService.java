package com.linguanova.idiomago.service.impl;

import com.linguanova.idiomago.persistence.entity.CategoryEntity;
import com.linguanova.idiomago.persistence.repository.ICategoryRepository;
import com.linguanova.idiomago.presentation.dto.category.CategoryDTO;
import com.linguanova.idiomago.presentation.dto.category.CreateCategotyDTO;
import com.linguanova.idiomago.service.interfaces.ICategoryService;
import com.linguanova.idiomago.util.exception.DuplicateResourceException;
import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import com.linguanova.idiomago.util.mapper.impl.category.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(ICategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDTO> getAll() {
        return mapper.mapToList(repository.findAll());
    }

    @Override
    public CategoryDTO getById(Long categoryId) {
        CategoryEntity entity = repository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));
        return mapper.mapTo(entity);
    }

    @Override
    public CategoryDTO save(CreateCategotyDTO dto) {
        CategoryEntity category = mapper.mapFrom(dto);
        if(repository.existsByName(category.getName())){
            throw new DuplicateResourceException("This Category already exists");
        }

        CategoryEntity temCategoryDTO = repository.save(category);
        return mapper.mapTo(temCategoryDTO);
    }

    @Override
    public void delete(Long categoryId) {
        repository.deleteById(categoryId);
    }


}
