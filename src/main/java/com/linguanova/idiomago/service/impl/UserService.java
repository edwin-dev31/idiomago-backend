package com.linguanova.idiomago.service.impl;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.persistence.repository.IUserRepository;
import com.linguanova.idiomago.presentation.dto.user.CreateUserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserUpdateDTO;
import com.linguanova.idiomago.service.interfaces.IUserService;
import com.linguanova.idiomago.util.exception.DuplicateResourceException;
import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import com.linguanova.idiomago.util.mapper.impl.user.CreateUserMapper;
import com.linguanova.idiomago.util.mapper.impl.user.UpdateUserMapper;
import com.linguanova.idiomago.util.mapper.impl.user.UserMapper;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	private final IUserRepository repository;
	private final UserMapper userMapper;
	private final CreateUserMapper createUserMapper;
	private final UpdateUserMapper updateUserMapper;
	private final PasswordEncoder passwordEncoder;

	public UserService(IUserRepository repository, UserMapper userMapper,
                       CreateUserMapper createUserMapper, UpdateUserMapper updateUserMapper, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.userMapper = userMapper;
		this.createUserMapper = createUserMapper;
        this.updateUserMapper = updateUserMapper;
        this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<UserDTO> findAll() {
		return userMapper.mapToList(repository.findAll());
	}

	@Override
	public Optional<UserDTO> findById(Long id) {
		return repository.findById(id)
			.map(userMapper::mapTo);
	}

	@Override
	public Optional<UserEntity> findByEmail(String userName) {
		return repository.findByEmail(userName);
	}

	@Override
	public UserDTO save(CreateUserDTO dto) {
		UserEntity entity = createUserMapper.mapFrom(dto);

		if (repository.findByEmail(dto.getEmail()).isPresent()){
			throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
		}

		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		UserEntity saved = repository.save(entity);
		return userMapper.mapTo(saved);
	}

	@Override
	public UserDTO update(Long userId, UserUpdateDTO user) {
		return repository.findById(userId)
			.map(existing -> {

				updateIfPresent(user.getUsername(), existing::setUsername);
				updateIfPresent(user.getEmail(), existing::setEmail);
				if (user.getPassword() != null && !user.getPassword().isBlank()) {
					if (isPasswordEncoded(user.getPassword())) {
						existing.setPassword(user.getPassword());
					} else {
						existing.setPassword(passwordEncoder.encode(user.getPassword()));
					}
				}

				updateIfPresent(user.getImageUrl(), existing::setImageUrl);
				updateIfPresent(user.getImagePublicId(), existing::setImagePublicId);

				if (user.getVerified() != null) {
					existing.setVerified(user.getVerified());
				}

				UserEntity updated = repository.save(existing);
				return userMapper.mapTo(updated);
			})
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	private void updateIfPresent(String value, Consumer<String> setter) {
		if (value != null && !value.isBlank()) {
			setter.accept(value);
		}
	}

	private boolean isPasswordEncoded(String password) {
		return password != null && password.matches("^\\$2[aby]\\$.{56}$");
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
