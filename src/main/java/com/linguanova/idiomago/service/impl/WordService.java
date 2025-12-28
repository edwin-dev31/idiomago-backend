package com.linguanova.idiomago.service.impl;

import com.linguanova.idiomago.persistence.entity.WordEntity;
import com.linguanova.idiomago.persistence.repository.IWordRepository;
import com.linguanova.idiomago.presentation.dto.word.CreateWordDTO;
import com.linguanova.idiomago.presentation.dto.word.WordDTO;
import com.linguanova.idiomago.service.interfaces.IWordService;
import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import com.linguanova.idiomago.util.mapper.impl.word.CreateWordMapper;
import com.linguanova.idiomago.util.mapper.impl.word.WordMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordService implements IWordService {
	private final IWordRepository repository;
	private final WordMapper mapper;
	private final CreateWordMapper createMapper;

	public WordService(IWordRepository repository, WordMapper mapper, CreateWordMapper createMapper) {
		this.repository = repository;
		this.mapper = mapper;
		this.createMapper = createMapper;
	}

	@Override
	public List<WordDTO> getAll() {
		List<WordEntity> wordEntities = repository.findAll();
		return mapper.mapToList(wordEntities);
	}

	@Override
	public Optional<WordDTO> getById(Long id) {
		return repository.findById(id)
			.map(mapper::mapTo);
	}

	@Override
	public WordDTO save(CreateWordDTO dto) {
		WordEntity wordEntity = createMapper.mapFrom(dto);
		WordEntity saved = repository.save(wordEntity);

		return mapper.mapTo(saved);
	}


	@Override
	public WordDTO update(Long id, CreateWordDTO wordDetails) {
		return repository.findById(id)
			.map(word -> {
				word.setName(wordDetails.getName());
				return mapper.mapTo(repository.save(word));
			}).orElseThrow(() -> new ResourceNotFoundException("Word not found with ID: " + id));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
