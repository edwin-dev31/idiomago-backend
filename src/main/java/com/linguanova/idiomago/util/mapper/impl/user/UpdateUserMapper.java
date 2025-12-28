package com.linguanova.idiomago.util.mapper.impl.user;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.presentation.dto.user.UserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserUpdateDTO;
import com.linguanova.idiomago.util.mapper.interfaces.user.IUpdateUserMapper;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserMapper {
	public IUpdateUserMapper mapper = IUpdateUserMapper.INSTANCE;

	public UserUpdateDTO mapTo(UserEntity userEntity){
		return mapper.mapTo(userEntity);
	}

	public UserUpdateDTO mapToUpdate(UserDTO userEntity){
		return mapper.mapToUpdate(userEntity);
	}
	public UserDTO mapFrom(UpdateUserMapper updateUserMapper){
		return mapper.mapFrom(updateUserMapper);
	}
}
