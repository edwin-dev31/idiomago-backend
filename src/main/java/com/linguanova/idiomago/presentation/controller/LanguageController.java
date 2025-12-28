package com.linguanova.idiomago.presentation.controller;

import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import com.linguanova.idiomago.presentation.dto.language.CreateLanguageDTO;
import com.linguanova.idiomago.service.interfaces.ILanguageService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/language")
public class LanguageController {

	private final ILanguageService service;

    public LanguageController(ILanguageService service) {
        this.service = service;
    }

    @GetMapping
	public List<LanguageEntity> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public LanguageEntity getById(@PathVariable Long id) {
		return service.getById(id)
			.orElseThrow(() -> new RuntimeException("Palabra no encontrada con ID: " + id));
	}

	@PostMapping
	public LanguageEntity save(@RequestBody CreateLanguageDTO lang) {
		return service.save(lang);
	}

	@PostMapping("/import")
	public ResponseEntity<String> importLanguages() {
		service.importLanguages();
		return ResponseEntity.ok("Languages imported successfully");
	}

	@PutMapping("/{id}")
	public LanguageEntity update(@PathVariable Long id, @RequestBody LanguageEntity lang) {
		return service.update(id, lang);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
