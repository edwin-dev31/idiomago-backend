package com.linguanova.idiomago.persistence.repository;

import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import java.util.List;

import com.linguanova.idiomago.util.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWordTransalationRepository extends JpaRepository<WordTranslationEntity, Long> {
	List<WordTranslationEntity> findBylanguageId(Long languageId);
	boolean existsByWordIdAndLanguageId(Long wordId, Long languageId);
	List<WordTranslationEntity> findByUserIdAndVisibility(Long userId, Visibility visibility);
	boolean existsByIdAndUserId(Long translationId, Long userId);

}