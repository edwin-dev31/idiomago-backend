package com.linguanova.idiomago.util.mapper.impl.wordTranslation;

import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.presentation.dto.wordTranslation.WordTranslationDTO;
import com.linguanova.idiomago.util.mapper.interfaces.wordTranslation.IWordTranslationMapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WordTranslationMapper implements IWordTranslationMapper{


	public WordTranslationDTO mapTo(WordTranslationEntity entity) {
		return INSTANCE.mapTo(entity);
	}

	public WordTranslationEntity mapFrom(WordTranslationDTO dto) {
		return INSTANCE.mapFrom(dto);
	}

	public List<WordTranslationDTO> mapToList(List<WordTranslationEntity> entities) {
		return INSTANCE.mapToList(entities);
	}

	public List<WordTranslationEntity> mapFromList(List<WordTranslationDTO> dtos) {
		return INSTANCE.mapFromList(dtos);
	}
}