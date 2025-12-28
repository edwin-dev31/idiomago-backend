package com.linguanova.idiomago.service.impl;

import com.linguanova.idiomago.external.images.UnsplashService;
import com.linguanova.idiomago.external.words.AIWordTranslationService;
import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.persistence.repository.*;
import com.linguanova.idiomago.persistence.view.TranslationView;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveMultipleWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveSingleWordTranslationDTO;
import com.linguanova.idiomago.service.interfaces.ITranslationViewService;
import com.linguanova.idiomago.util.Visibility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TranslationViewService implements ITranslationViewService {

	private final ITranslationView repository;
	private final IWordTransalationRepository iWordTransalationRepository;
	private final AIWordTranslationService aiWordTranslationService;
	private final UnsplashService unsplashService;

	public TranslationViewService(ITranslationView translationViewRepository,
                                  IWordTransalationRepository iWordTransalationRepository, AIWordTranslationService aiWordTranslationService, UnsplashService unsplashService) {
		this.repository = translationViewRepository;
		this.iWordTransalationRepository = iWordTransalationRepository;
        this.aiWordTranslationService = aiWordTranslationService;
        this.unsplashService = unsplashService;
    }

	@Override
	public List<TranslationView> findAll() {
		return repository.findAll().stream()
				.filter(e -> e.getVisibility() == Visibility.PUBLIC)
				.toList();
	}

	@Override
	public List<TranslationView> findToFavorite() {
		return repository.findAll();
	}

	@Override
	public List<TranslationView> saveMultipleWords(SaveMultipleWordTranslationDTO newMultipleTranslation) {
		List<TranslationView> results = searchAllViews(newMultipleTranslation.getWord(), newMultipleTranslation.getLanguageCode());
		if (!results.isEmpty()) return results;

		SaveMultipleWordTranslationDTO multiple =
			new SaveMultipleWordTranslationDTO(newMultipleTranslation.getUserId(), newMultipleTranslation.getWord(),
					newMultipleTranslation.getLanguageCode(), newMultipleTranslation.getCategoryId());
		WordTranslationEntity saved = aiWordTranslationService.saveMultipleWordsIA(multiple);

		return searchAllViews(newMultipleTranslation.getWord(), newMultipleTranslation.getLanguageCode());
	}

	@Override
	public List<TranslationView> saveSingleWords(SaveSingleWordTranslationDTO newTranslation){
		aiWordTranslationService.saveSingleWords(newTranslation);

		return searchAllViews(newTranslation.getWord(), newTranslation.getLanguageCode());
	};

	@Override
	public List<TranslationView> searchAllViews(String query, String languageCode) {
		List<TranslationView> matches = getAllByWord(query);

		if (matches.isEmpty()) {
			matches = repository.findByOriginalWordIgnoreCaseOrderByLanguageCode(query);
		}

		if (matches.isEmpty()) {
			return List.of();
		}

		Long wordId = matches.get(0).getWordId();
		return repository.findByWordIdAndLanguageCode(wordId, languageCode);
	}

	@Override
	public List<TranslationView> getAllByWord(String word) {
		return repository.findByTranslatedWordIgnoreCaseOrderByLanguageCode(word);
	}

	@Override
	public List<TranslationView> getAllByLanguage(String languageCode) {
		return repository.findByLanguageCodeOrderByOriginalWord(languageCode);
	}

	@Override
	public List<TranslationView> searchByLang(String lang) {
		return repository.findByLanguageCodeIgnoreCase(lang);
	}

	@Override
	public List<TranslationView> searchByCategory(long categoryId) {
		return repository.findByCategoryId(categoryId);
	}

	@Override
	public List<TranslationView> searchByDescription(String partial) {
		return repository.findByTranslatedDescriptionContainingIgnoreCase(partial);
	}

	@Override
	public List<TranslationView> searchByPartialExample(String partial) {
		return repository.findByTranslatedExampleContainingIgnoreCase(partial);
	}

	@Override
	public List<TranslationView> searchByPartialWord(String partial) {
		return repository.findByTranslatedWordContainingIgnoreCase(partial);
	}

	private String searchImage(String word) {
		return UnsplashService.getImageUrlForWord(word);
	}

	@Override
	public void importImages() {
		List<TranslationView> translations = repository.findAll();
		List<WordTranslationEntity> temTranslation = new ArrayList<>();

		for (TranslationView item : translations) {
			if (item.getImageUrl() == null || item.getImageUrl().isBlank()) {
				Long wordTranslationId = item.getWordTranslationId();
				Optional<WordTranslationEntity> temWord = iWordTransalationRepository.findById(wordTranslationId);

				temWord.ifPresent(wordTranslation -> {
					String word = wordTranslation.getWord().getName();
					String generatedUrl = searchImage(word);
					wordTranslation.setImageUrl(generatedUrl);
					temTranslation.add(wordTranslation);
				});
			}
		}

		iWordTransalationRepository.saveAll(temTranslation);
	}

	@Override
	public String changeImages(Long wordTranslationId) {
		Optional<WordTranslationEntity> temWord = iWordTransalationRepository.findById(wordTranslationId);

		if (temWord.isPresent()) {
			WordTranslationEntity wordTranslation = temWord.get();
			String word = wordTranslation.getWord().getName();
			String generatedUrl = searchImage(word);

			wordTranslation.setImageUrl(generatedUrl);
			iWordTransalationRepository.save(wordTranslation);

			return generatedUrl;
		}
		return null;
	}

	@Override
	public List<TranslationView> findByUser(Long userId) {
		return repository.findByUserId(userId);
	}
}
