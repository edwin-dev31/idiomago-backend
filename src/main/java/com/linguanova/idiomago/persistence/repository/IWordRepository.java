package com.linguanova.idiomago.persistence.repository;

import com.linguanova.idiomago.persistence.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IWordRepository extends JpaRepository<WordEntity, Long> {
    Optional<WordEntity> findByNameIgnoreCase(String word);
}
