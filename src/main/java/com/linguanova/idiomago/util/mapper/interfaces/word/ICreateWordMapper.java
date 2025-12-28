package com.linguanova.idiomago.util.mapper.interfaces.word;

import com.linguanova.idiomago.persistence.entity.WordEntity;
import com.linguanova.idiomago.presentation.dto.word.CreateWordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICreateWordMapper {
	ICreateWordMapper INSTANCE = Mappers.getMapper(ICreateWordMapper.class);

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
		@Mapping(target = "translations", ignore = true)
	})
	WordEntity mapFrom(CreateWordDTO wordDTO);
}
