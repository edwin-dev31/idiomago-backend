package com.linguanova.idiomago.util.mapper.interfaces.user;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.presentation.dto.user.UserDTO;
import com.linguanova.idiomago.util.mapper.interfaces.MapperGeneric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUserMapper extends MapperGeneric<UserEntity, UserDTO> {
	IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
}
