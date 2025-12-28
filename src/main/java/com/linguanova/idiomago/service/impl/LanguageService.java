package com.linguanova.idiomago.service.impl;


import com.linguanova.idiomago.external.language.LanguageApiClient;
import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import com.linguanova.idiomago.persistence.repository.ILanguageRepository;
import com.linguanova.idiomago.presentation.dto.language.CreateLanguageDTO;
import com.linguanova.idiomago.service.interfaces.ILanguageService;
import com.linguanova.idiomago.util.mapper.impl.language.CreateLanguageMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LanguageService implements ILanguageService {

	private final ILanguageRepository repository;
	private final CreateLanguageMapper mapper;
	private final LanguageApiClient languageApiClient;

	public LanguageService(ILanguageRepository repository, CreateLanguageMapper mapper, LanguageApiClient languageApiClient) {
		this.repository = repository;
		this.mapper = mapper;
        this.languageApiClient = languageApiClient;
    }

	@Override
	public List<LanguageEntity> getAll() {
		return repository.findAll();
	}

	@Override
	public Optional<LanguageEntity> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public LanguageEntity save(CreateLanguageDTO languageDTO) {
		LanguageEntity languageEntity = mapper.mapFrom(languageDTO);
		return repository.save(languageEntity);
	}

	@Override
	public LanguageEntity update(Long id, LanguageEntity langDetails) {
		return repository.findById(id)
			.map(word -> {
				word.setName(langDetails.getName());
				return repository.save(word);
			}).orElseThrow(() -> new RuntimeException("Language not found with ID: " + id));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void importLanguages() {

		List<Map<String, String>> externalLanguages = languageApiClient.fetchLanguagesFromExternalApi();

		Set<String> existingCodes = repository.findAll()
				.stream()
				.map(LanguageEntity::getCode)
				.collect(Collectors.toSet());

		List<LanguageEntity> languagesToSave = externalLanguages.stream()
				.filter(lang -> !existingCodes.contains(lang.get("code")))
				.map(lang -> {
					CreateLanguageDTO dto = new CreateLanguageDTO();
					dto.setCode(lang.get("code"));
					dto.setName(lang.get("name"));
					return mapper.mapFrom(dto);
				})
				.toList();

		repository.saveAll(languagesToSave);
	}

}