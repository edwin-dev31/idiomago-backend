package com.linguanova.idiomago.util.mapper.interfaces.wordTranslation;

import com.linguanova.idiomago.presentation.dto.wordTranslation.CreateWordTranslationDTO;
import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import com.linguanova.idiomago.persistence.entity.WordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICreateWordTranslationMapper {
	ICreateWordTranslationMapper INSTANCE = Mappers.getMapper(ICreateWordTranslationMapper.class);

	@Mappings({
		@Mapping(target = "translatedWord", source = "dto.translatedWord"),
		@Mapping(target = "translatedExample", source = "dto.translatedExample"),
		@Mapping(target = "translatedDescription", source = "dto.translatedDescription"),
		@Mapping(target = "imageUrl", source = "dto.imageUrl"),
		@Mapping(target = "audioUrl", source = "dto.audioUrl"),
		@Mapping(target = "word", source = "word"),
		@Mapping(target = "language", source = "language"),
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
		@Mapping(target = "favoritedBy", ignore = true)
	})
	WordTranslationEntity mapFrom(CreateWordTranslationDTO dto, WordEntity word, LanguageEntity language);
}
