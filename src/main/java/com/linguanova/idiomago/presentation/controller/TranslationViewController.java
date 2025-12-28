package com.linguanova.idiomago.presentation.controller;

import com.linguanova.idiomago.persistence.view.TranslationView;
import com.linguanova.idiomago.presentation.dto.wordTranslation.CreateMultipleWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveMultipleWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveSingleWordTranslationDTO;
import com.linguanova.idiomago.service.interfaces.ITranslationViewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/view")
public class TranslationViewController {

	private final ITranslationViewService translationViewService;

	public TranslationViewController(ITranslationViewService translationViewService) {
		this.translationViewService = translationViewService;
    }

	@GetMapping()
	public ResponseEntity<List<TranslationView>> findAll(){
		return (ResponseEntity.ok(translationViewService.findAll()));
	}

	@GetMapping("/favorite")
	public ResponseEntity<List<TranslationView>> findToFavorite(){
		return (ResponseEntity.ok(translationViewService.findToFavorite()));
	}

	@GetMapping("/my-words/{userId}")
	public ResponseEntity<List<TranslationView>> myWords(
			@PathVariable Long userId
	) {
		return ResponseEntity.ok(translationViewService.findByUser(userId));
	}

	@PostMapping("/save/word/multiple")
	public ResponseEntity<?> saveMultipleWords(@RequestBody CreateMultipleWordTranslationDTO dto) {
		List<TranslationView> results = dto.getLanguageCodes().stream()
				.map(code -> {
					SaveMultipleWordTranslationDTO singleDTO = new SaveMultipleWordTranslationDTO(
							dto.getUserId(), dto.getWord(), code, dto.getCategoryId()
					);
					return translationViewService.saveMultipleWords(singleDTO);
				})
				.flatMap(List::stream)
				.toList();

		if (results.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No translations found.");
		}
		return ResponseEntity.ok(results);
	}


	@PostMapping("/save/word/single")
	public ResponseEntity<?> saveSingleWords(@RequestBody SaveSingleWordTranslationDTO newTranslation) {
		List<TranslationView> result = translationViewService.saveSingleWords(newTranslation);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No translations found.");
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/word/{word}")
	public ResponseEntity<List<TranslationView>> getAllByWord(@PathVariable String word) {
		return ResponseEntity.ok(translationViewService.getAllByWord(word));
	}

	@GetMapping("/language/{languageCode}")
	public ResponseEntity<List<TranslationView>> getAllByLanguage(@PathVariable String languageCode) {
		return ResponseEntity.ok(translationViewService.getAllByLanguage(languageCode));
	}

	@GetMapping("/search/lang/{lang}")
	public ResponseEntity<List<TranslationView>> searchByLang(@PathVariable String lang) {
		return ResponseEntity.ok(translationViewService.searchByLang(lang));
	}

	@GetMapping("/search/categories/{categoryId}")
	public ResponseEntity<List<TranslationView>> searchByCategory(@PathVariable Long categoryId) {
		return ResponseEntity.ok(translationViewService.searchByCategory(categoryId));
	}

	@GetMapping("/search/description/{partial}")
	public ResponseEntity<List<TranslationView>> searchByDescription(@PathVariable String partial) {
		return ResponseEntity.ok(translationViewService.searchByDescription(partial));
	}

	@GetMapping("/search/example/{partial}")
	public ResponseEntity<List<TranslationView>> searchByPartialExample(@PathVariable String partial) {
		return ResponseEntity.ok(translationViewService.searchByPartialExample(partial));
	}

    @GetMapping("/search/word/{partial}")
	public ResponseEntity<List<TranslationView>> searchByPartialWord(@PathVariable String partial) {
		return ResponseEntity.ok(translationViewService.searchByPartialWord(partial));
	}

	@PostMapping("/images/import")
	public ResponseEntity<String> importImages(){
		translationViewService.importImages();
		return ResponseEntity.ok("Images imported successfully");
	}

	@PostMapping("/images/change/{wordTranslationId}")
	public ResponseEntity<Map<String, String>> changeImages(@PathVariable Long wordTranslationId) {
		String newUrl = translationViewService.changeImages(wordTranslationId);
		return ResponseEntity.ok(Map.of("message", newUrl));
	}
}
