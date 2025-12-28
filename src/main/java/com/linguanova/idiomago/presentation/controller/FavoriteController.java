package com.linguanova.idiomago.presentation.controller;

import com.linguanova.idiomago.presentation.dto.favorite.CreateFavoriteDTO;
import com.linguanova.idiomago.presentation.dto.favorite.FavoriteDTO;
import com.linguanova.idiomago.service.impl.FavoriteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

	private final FavoriteService service;

	public FavoriteController(FavoriteService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<FavoriteDTO> create(@RequestBody CreateFavoriteDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}

	@DeleteMapping("/{userId}/{wordTranslationId}")
	public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long wordTranslationId) {
		service.delete(userId, wordTranslationId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<FavoriteDTO>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<FavoriteDTO>> getByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(service.getByUserId(userId));
	}
}
