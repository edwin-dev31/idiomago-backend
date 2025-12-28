package com.linguanova.idiomago.util.mapper.impl.category;

import com.linguanova.idiomago.persistence.entity.CategoryEntity;
import com.linguanova.idiomago.presentation.dto.category.CategoryDTO;
import com.linguanova.idiomago.presentation.dto.category.CreateCategotyDTO;
import com.linguanova.idiomago.util.mapper.interfaces.category.ICategoryMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper implements ICategoryMapper {

    @Override
    public CategoryDTO mapTo(CategoryEntity category) {
        return INSTANCE.mapTo(category);
    }

    @Override
    public List<CategoryDTO> mapToList(List<CategoryEntity> categories) {
        return INSTANCE.mapToList(categories);
    }

    @Override
    public CategoryEntity mapFrom(CreateCategotyDTO categoryDTO) {
        return INSTANCE.mapFrom(categoryDTO);
    }
}
