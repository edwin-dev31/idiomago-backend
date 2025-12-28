package com.linguanova.idiomago.service.interfaces;

import com.linguanova.idiomago.presentation.dto.wordTranslation.CreateWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.UpdateWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.WordTranslationDTO;
import java.util.List;
import java.util.Optional;

public interface IWordTranslationService {
	List<WordTranslationDTO> getAll();
	Optional<WordTranslationDTO> getById(Long id);
	WordTranslationDTO save(CreateWordTranslationDTO word);
	WordTranslationDTO update(Long id, UpdateWordTranslationDTO word);
	void delete(Long id,  Long userId);
	List<WordTranslationDTO> getByLangId(Long langId);
}
