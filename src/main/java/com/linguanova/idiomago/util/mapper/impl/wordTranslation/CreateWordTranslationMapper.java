package com.linguanova.idiomago.util.mapper.impl.wordTranslation;

import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import com.linguanova.idiomago.persistence.entity.WordEntity;
import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.presentation.dto.wordTranslation.CreateWordTranslationDTO;
import com.linguanova.idiomago.util.mapper.interfaces.wordTranslation.ICreateWordTranslationMapper;
import org.springframework.stereotype.Component;

@Component
public class CreateWordTranslationMapper {
	private final ICreateWordTranslationMapper mapper = ICreateWordTranslationMapper.INSTANCE;

	public WordTranslationEntity mapFrom(CreateWordTranslationDTO entity, WordEntity word, LanguageEntity language) {
		return mapper.mapFrom(entity, word, language);
	}
}
