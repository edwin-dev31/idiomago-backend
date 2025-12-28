package com.linguanova.idiomago.util.mapper.impl.language;

import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import com.linguanova.idiomago.presentation.dto.language.CreateLanguageDTO;
import com.linguanova.idiomago.util.mapper.interfaces.language.ICreateLanguageMapper;
import org.springframework.stereotype.Component;

@Component
public class CreateLanguageMapper {
	public ICreateLanguageMapper mapper = ICreateLanguageMapper.INSTANCE;

	public LanguageEntity mapFrom(CreateLanguageDTO createDto){
		return mapper.mapFrom(createDto);
	}
}