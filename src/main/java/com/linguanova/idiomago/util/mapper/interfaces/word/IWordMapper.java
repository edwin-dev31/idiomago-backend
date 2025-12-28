package com.linguanova.idiomago.util.mapper.interfaces.word;

import com.linguanova.idiomago.persistence.entity.WordEntity;
import com.linguanova.idiomago.presentation.dto.word.WordDTO;
import com.linguanova.idiomago.util.mapper.interfaces.MapperGeneric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IWordMapper extends MapperGeneric<WordEntity, WordDTO> {
	IWordMapper INSTANCE = Mappers.getMapper(IWordMapper.class);
}
