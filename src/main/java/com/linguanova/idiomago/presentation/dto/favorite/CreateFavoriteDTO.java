package com.linguanova.idiomago.presentation.dto.favorite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateFavoriteDTO {
	private Long userId;
	private Long wordTranslationId;
}
