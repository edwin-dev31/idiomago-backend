package com.linguanova.idiomago.util.mapper.impl.word;

import com.linguanova.idiomago.persistence.entity.WordEntity;
import com.linguanova.idiomago.presentation.dto.word.CreateWordDTO;
import com.linguanova.idiomago.util.mapper.interfaces.word.ICreateWordMapper;
import org.springframework.stereotype.Component;

@Component
public class CreateWordMapper {
	public ICreateWordMapper mapper = ICreateWordMapper.INSTANCE;

	public WordEntity mapFrom(CreateWordDTO createDto){
		return mapper.mapFrom(createDto);
	}
}
