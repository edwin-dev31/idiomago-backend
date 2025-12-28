package com.linguanova.idiomago.util.mapper.impl.user;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.presentation.dto.user.CreateUserDTO;
import com.linguanova.idiomago.util.mapper.interfaces.user.ICreateUserMapper;
import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper {
	public ICreateUserMapper mapper = ICreateUserMapper.INSTANCE;

	public UserEntity mapFrom(CreateUserDTO createUserDto){
		return mapper.mapFrom(createUserDto);
	}
}
