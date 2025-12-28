package com.linguanova.idiomago.service.interfaces;

import com.linguanova.idiomago.persistence.view.TranslationView;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveMultipleWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveSingleWordTranslationDTO;

import java.util.List;

public interface ITranslationViewService {
	List<TranslationView> findAll();
	List<TranslationView> findToFavorite();
	List<TranslationView> findByUser(Long userId);
	List<TranslationView> saveMultipleWords(SaveMultipleWordTranslationDTO newMultipleTranslation);
	List<TranslationView> saveSingleWords(SaveSingleWordTranslationDTO newTranslation);
	List<TranslationView> searchAllViews(String query, String languageCode);
	List<TranslationView> getAllByWord(String word);
	List<TranslationView> getAllByLanguage(String languageCode);
	List<TranslationView> searchByLang(String lang);
	List<TranslationView> searchByCategory(long categoryId);
	List<TranslationView> searchByDescription(String partial);
	List<TranslationView> searchByPartialExample(String partial);
	List<TranslationView> searchByPartialWord(String partial);

	void importImages();
	String changeImages(Long wordTranslationId);
}
