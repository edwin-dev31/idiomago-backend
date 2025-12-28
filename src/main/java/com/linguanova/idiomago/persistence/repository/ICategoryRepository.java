package com.linguanova.idiomago.persistence.repository;

import com.linguanova.idiomago.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Boolean existsByName(String name);
}
