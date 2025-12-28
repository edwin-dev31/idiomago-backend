package com.linguanova.idiomago.service.interfaces;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.presentation.dto.user.CreateUserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
	List<UserDTO> findAll();
	Optional<UserDTO> findById(Long id);
	Optional<UserEntity> findByEmail(String email);
	UserDTO save(CreateUserDTO user);
	UserDTO update(Long userId, UserUpdateDTO user);
	void delete(Long id);
}
