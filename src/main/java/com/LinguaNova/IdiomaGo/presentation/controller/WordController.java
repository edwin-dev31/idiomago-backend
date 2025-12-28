package com.LinguaNova.IdiomaGo.presentation.controller;

import com.LinguaNova.IdiomaGo.presentation.dto.word.CreateWordDTO;
import com.LinguaNova.IdiomaGo.presentation.dto.word.WordDTO;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.LinguaNova.IdiomaGo.service.interfaces.IWordService;

@RestController
@RequestMapping("api/word")
public class WordController {

	private final IWordService wordService;

    public WordController(IWordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping
	public List<WordDTO> getAllWords() {
		return wordService.getAll();
	}

	@GetMapping("/{id}")
	public WordDTO getWordById(@PathVariable Long id) {
		return wordService.getById(id)
			.orElseThrow(() -> new RuntimeException("Palabra no encontrada con ID: " + id));
	}


	@PostMapping
	public WordDTO createWord(@RequestBody CreateWordDTO word) {
		return wordService.save(word);
	}

	@PutMapping("/{id}")
	public WordDTO updateWord(@PathVariable Long id, @RequestBody CreateWordDTO word) {
		return wordService.update(id, word);
	}

	@DeleteMapping("/{id}")
	public void deleteWord(@PathVariable Long id) {
		wordService.delete(id);
	}
}
