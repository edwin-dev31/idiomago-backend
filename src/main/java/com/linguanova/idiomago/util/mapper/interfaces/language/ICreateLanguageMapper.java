package com.linguanova.idiomago.util.mapper.interfaces.language;

import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import com.linguanova.idiomago.presentation.dto.language.CreateLanguageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICreateLanguageMapper {
	ICreateLanguageMapper INSTANCE = Mappers.getMapper(ICreateLanguageMapper.class);

	LanguageEntity mapFrom(CreateLanguageDTO createDto);
}
