package com.linguanova.idiomago.presentation.controller;

import com.linguanova.idiomago.external.cloudinary.CloudinaryResponse;
import com.linguanova.idiomago.external.cloudinary.CloudinaryService;
import com.linguanova.idiomago.presentation.dto.user.CreateUserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserDTO;
import com.linguanova.idiomago.presentation.dto.user.UserUpdateDTO;
import com.linguanova.idiomago.service.interfaces.IUserService;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import com.linguanova.idiomago.util.mapper.impl.user.UpdateUserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final IUserService userService;
	private final CloudinaryService cloudinaryService;
	private final UpdateUserMapper updateUserMapper;
	public UserController(IUserService userService, CloudinaryService cloudinaryService, UpdateUserMapper updateUserMapper) {
		this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.updateUserMapper = updateUserMapper;
    }

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAll() {
		List<UserDTO> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
		return userService.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserDTO dto) {
		UserDTO created = userService.save(dto);
		return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO user) {
		try {
			UserDTO updated = userService.update(id, user);
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/avatar")
	public ResponseEntity<?> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		UserDTO user = userService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		try {
			String previousPublicId = user.getImagePublicId();
			if (previousPublicId != null && !previousPublicId.isBlank()) {
				cloudinaryService.delete(previousPublicId);
			}

			CloudinaryResponse uploadResult = cloudinaryService.upload(file);
			user.setImageUrl(uploadResult.getImageUrl());
			user.setImagePublicId(uploadResult.getPublicId());

			UserDTO updated = userService.update(id, updateUserMapper.mapper.mapToUpdate(user));
			return ResponseEntity.ok(updated);

		} catch (IOException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("error", "Error reading uploaded file"));
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Unexpected error: " + e.getMessage()));
		}
	}


}