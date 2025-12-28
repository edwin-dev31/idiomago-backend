package com.linguanova.idiomago.service.impl;

import com.linguanova.idiomago.persistence.entity.FavoriteEntity;
import com.linguanova.idiomago.persistence.entity.FavoriteId;
import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.persistence.repository.IFavoriteRepository;
import com.linguanova.idiomago.persistence.repository.IUserRepository;
import com.linguanova.idiomago.persistence.repository.IWordTransalationRepository;
import com.linguanova.idiomago.presentation.dto.favorite.CreateFavoriteDTO;
import com.linguanova.idiomago.presentation.dto.favorite.FavoriteDTO;
import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import com.linguanova.idiomago.util.mapper.interfaces.favorite.IFavoriteMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FavoriteService {

	private final IFavoriteRepository repository;
	private final IUserRepository userRepository;
	private final IWordTransalationRepository wordTranslationRepository;
	private final IFavoriteMapper mapper;

	public FavoriteService(IFavoriteRepository repository, IUserRepository userRepository,
		IWordTransalationRepository wordTranslationRepository, IFavoriteMapper mapper) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.wordTranslationRepository = wordTranslationRepository;
		this.mapper = mapper;
	}

	public FavoriteDTO save(CreateFavoriteDTO dto) {
		UserEntity user = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		WordTranslationEntity wordTranslation = wordTranslationRepository.findById(dto.getWordTranslationId())
			.orElseThrow(() -> new ResourceNotFoundException("Word translation not found"));

		FavoriteEntity entity = mapper.mapFrom(dto, user, wordTranslation);
		FavoriteId favoriteId = new FavoriteId(dto.getUserId(), dto.getWordTranslationId());
		entity.setId(favoriteId);

		return mapper.mapTo(repository.save(entity));
	}

	public void delete(Long userId, Long wordTranslationId) {
		FavoriteId id = new FavoriteId(userId, wordTranslationId);
		if (!repository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorite not found");
		}
		repository.deleteById(id);
	}

	public List<FavoriteDTO> getAll() {
		return repository.findAll().stream().map(mapper::mapTo).toList();
	}

	public List<FavoriteDTO> getByUserId(Long userId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		return repository.findAllByUserId(userId).stream().map(mapper::mapTo).toList();
	}
}
