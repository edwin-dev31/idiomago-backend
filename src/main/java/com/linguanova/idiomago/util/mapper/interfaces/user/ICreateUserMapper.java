package com.linguanova.idiomago.util.mapper.interfaces.user;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.presentation.dto.user.CreateUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICreateUserMapper {
	ICreateUserMapper INSTANCE = Mappers.getMapper(ICreateUserMapper.class);
	UserEntity mapFrom(CreateUserDTO createUserDto);
}
