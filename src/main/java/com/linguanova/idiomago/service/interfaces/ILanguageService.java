package com.linguanova.idiomago.service.interfaces;

import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import com.linguanova.idiomago.presentation.dto.language.CreateLanguageDTO;
import java.util.List;
import java.util.Optional;

public interface ILanguageService {
	List<LanguageEntity> getAll();
	Optional<LanguageEntity> getById(Long id);
	LanguageEntity save(CreateLanguageDTO languageDTO);
	LanguageEntity update(Long id, LanguageEntity languageEntity);
	void delete(Long id);
	void importLanguages();
}
