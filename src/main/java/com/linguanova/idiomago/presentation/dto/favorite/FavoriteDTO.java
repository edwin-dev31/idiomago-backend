package com.linguanova.idiomago.presentation.dto.favorite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteDTO {
	private Long userId;
	private Long wordTranslationId;
	private LocalDateTime createdAt;
}
