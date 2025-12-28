package com.linguanova.idiomago.service.interfaces;

import com.linguanova.idiomago.presentation.dto.favorite.CreateFavoriteDTO;
import com.linguanova.idiomago.presentation.dto.favorite.FavoriteDTO;
import java.util.List;

public interface IFavoriteService {
	FavoriteDTO save(CreateFavoriteDTO dto);
	void delete(Long userId, Long wordTranslationId);
	List<FavoriteDTO> getAll();
}
