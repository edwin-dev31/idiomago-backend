package com.linguanova.idiomago.util.mapper.interfaces.wordTranslation;

import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.presentation.dto.wordTranslation.WordTranslationDTO;

import com.linguanova.idiomago.util.mapper.interfaces.MapperGeneric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IWordTranslationMapper extends
	MapperGeneric<WordTranslationEntity, WordTranslationDTO> {
	IWordTranslationMapper INSTANCE = Mappers.getMapper(IWordTranslationMapper.class);

}