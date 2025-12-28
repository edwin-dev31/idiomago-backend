package com.linguanova.idiomago.service.impl;

import com.linguanova.idiomago.persistence.entity.*;
import com.linguanova.idiomago.persistence.repository.ICategoryRepository;
import com.linguanova.idiomago.persistence.repository.ILanguageRepository;
import com.linguanova.idiomago.persistence.repository.IWordRepository;
import com.linguanova.idiomago.persistence.repository.IWordTransalationRepository;
import com.linguanova.idiomago.presentation.dto.wordTranslation.CreateWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.UpdateWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.WordTranslationDTO;
import com.linguanova.idiomago.service.interfaces.IWordTranslationService;
import com.linguanova.idiomago.util.Visibility;
import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import com.linguanova.idiomago.util.exception.UnauthorizedException;
import com.linguanova.idiomago.util.mapper.impl.wordTranslation.CreateWordTranslationMapper;
import com.linguanova.idiomago.util.mapper.impl.wordTranslation.WordTranslationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordTranslationService implements IWordTranslationService {

	private final IWordTransalationRepository repository;
	private final IWordRepository wordRepository;
	private final ILanguageRepository languageRepository;
	private final ICategoryRepository categoryRepository;
	private final WordTranslationMapper mapper;
	private final CreateWordTranslationMapper createMapper;

	public WordTranslationService(IWordTransalationRepository repository,
                                  IWordRepository wordRepository, ILanguageRepository languageRepository, ICategoryRepository categoryRepository, WordTranslationMapper mapper,
                                  CreateWordTranslationMapper createMapper) {
		this.repository = repository;
		this.wordRepository = wordRepository;
		this.languageRepository = languageRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
		this.createMapper = createMapper;
	}

	@Override
	public List<WordTranslationDTO> getAll() {
		List<WordTranslationEntity> entities = repository.findAll().stream()
				.filter(e -> e.getVisibility() == Visibility.PUBLIC)
				.toList();
		return mapper.mapToList(entities);
	}

	@Override
	public Optional<WordTranslationDTO> getById(Long id) {
		Optional<WordTranslationEntity> result = repository.findById(id);
		return result.map(mapper::mapTo);
	}

	@Override
	public WordTranslationDTO save(CreateWordTranslationDTO wordTranslationDTO) {
		WordEntity word = wordRepository.findById(wordTranslationDTO.getWordId())
			.orElseThrow(() -> new ResourceNotFoundException("Word not found with ID: " +
					wordTranslationDTO.getWordId()));

		LanguageEntity language = languageRepository.findById(wordTranslationDTO.getLanguageId())
			.orElseThrow(() -> new ResourceNotFoundException("Language not found with ID: " +
					wordTranslationDTO.getLanguageId()));

		WordTranslationEntity entity = createMapper.mapFrom(wordTranslationDTO, word, language);

		WordTranslationEntity saved = repository.save(entity);
		return mapper.mapTo(saved);
	}

	public WordTranslationEntity saveTranslationFull(
				UserEntity user, String originalWord, String translatedWord,
		 		String example, String description, String languageCode, Long categoryId,
				String imageUrl, Visibility visibility) {

		WordEntity word = findOrCreateWord(originalWord);
		LanguageEntity language = getLanguageOrThrow(languageCode);
		CategoryEntity category = getCategoryOrThrow(categoryId);


		WordTranslationEntity entity = new WordTranslationEntity();
		entity.setUser(user);
		entity.setWord(word);
		entity.setLanguage(language);
		entity.setCategory(category);
		entity.setTranslatedWord(translatedWord);
		entity.setTranslatedExample(example);
		entity.setTranslatedDescription(description);
		entity.setImageUrl(imageUrl);
		entity.setVisibility(visibility);

		return repository.save(entity);
	}

	@Override
	public WordTranslationDTO update(Long id, UpdateWordTranslationDTO dto) {
		if (!canUserEditTranslation(id, dto.getUserId())) {
			throw new UnauthorizedException("You are not enabled to update this translation.");
		}

		WordTranslationEntity entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Translation not found with id: " + id));

		entity.setTranslatedWord(dto.getTranslatedWord());
		entity.setTranslatedExample(dto.getTranslatedExample());
		entity.setTranslatedDescription(dto.getTranslatedDescription());

		WordTranslationEntity updated = repository.save(entity);

		// Convertir a DTO (aquí puedes usar un mapper si tienes)
		WordTranslationDTO result = new WordTranslationDTO();
		result.setId(updated.getId());
		result.setTranslatedWord(updated.getTranslatedWord());
		result.setTranslatedExample(updated.getTranslatedExample());
		result.setTranslatedDescription(updated.getTranslatedDescription());
		result.setImageUrl(entity.getImageUrl());

		return result;
	}


	@Override
	public void delete(Long id, Long userId) {
		if (!canUserEditTranslation(id, userId)) {
			throw new UnauthorizedException("You are not enabled to delete this translation.");
		}

		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Translation not found with id: " + id);
		}

		repository.deleteById(id);
	}


	public boolean canUserEditTranslation(Long translationId, Long userId) {
		return repository.existsByIdAndUserId(translationId, userId);
	}

	@Override
	public List<WordTranslationDTO> getByLangId(Long langId) {
		List<WordTranslationEntity> list = repository.findBylanguageId(langId);
		return mapper.mapToList(list);
	}

	private WordEntity findOrCreateWord(String word) {
		return wordRepository.findByNameIgnoreCase(word)
				.orElseGet(() -> {
					WordEntity newWord = new WordEntity();
					newWord.setName(word);
					return wordRepository.save(newWord);
				});
	}

	private LanguageEntity getLanguageOrThrow(String code) {
		LanguageEntity lang = languageRepository.findByCode(code);
		if (lang == null) {
			throw new ResourceNotFoundException("Language not found: " + code);
		}
		return lang;
	}

	private CategoryEntity getCategoryOrThrow(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
	}


	public List<WordTranslationDTO> findByUserAndVisibility(Long userId, Visibility visibility) {
		List<WordTranslationEntity> list = repository.findByUserIdAndVisibility(userId, visibility);
		return mapper.mapToList(list);
	}

}
