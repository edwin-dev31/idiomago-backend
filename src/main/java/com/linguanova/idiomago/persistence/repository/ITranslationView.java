package com.linguanova.idiomago.persistence.repository;

import com.linguanova.idiomago.persistence.view.TranslationView;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITranslationView extends JpaRepository<TranslationView, Long> {

	List<TranslationView> findByUserId(Long userId);
	Optional<TranslationView> findByTranslatedWordAndLanguageCode(String word, String languageCode);

	List<TranslationView> findByWordIdAndLanguageCode(Long wordId, String languageCode);
	List<TranslationView> findByOriginalWordIgnoreCaseOrderByLanguageCode(String word);
	List<TranslationView> findByTranslatedWordIgnoreCaseOrderByLanguageCode(String translatedWord);

	List<TranslationView> findByLanguageCodeOrderByOriginalWord(String languageCode);

	List<TranslationView> findByTranslatedWordContainingIgnoreCase(String partialWord);

	List<TranslationView> findByLanguageCodeIgnoreCase(String languageCode);
	List<TranslationView> findByCategoryId(Long categoryId);
	List<TranslationView> findByTranslatedDescriptionContainingIgnoreCase(String partial);
	List<TranslationView> findByTranslatedExampleContainingIgnoreCase(String partial);
}
