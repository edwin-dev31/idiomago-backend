package com.linguanova.idiomago.util.mapper.interfaces.user;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.presentation.dto.user.UserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserUpdateDTO;
import com.linguanova.idiomago.util.mapper.impl.user.UpdateUserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUpdateUserMapper {
	IUpdateUserMapper INSTANCE = Mappers.getMapper(IUpdateUserMapper.class);
	UserDTO mapFrom(UpdateUserMapper updateUserMapper);
	UserUpdateDTO mapTo(UserEntity updateUserMapper);
	UserUpdateDTO mapToUpdate(UserDTO updateUserMapper);
}
