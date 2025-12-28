package com.linguanova.idiomago.persistence.repository;

import com.linguanova.idiomago.persistence.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
    List<ReactionEntity> findByWordTranslationId(Long wordTranslationId);
    Optional<ReactionEntity> findByUserIdAndWordTranslationId(Long userId, Long wordTranslationId);

}
