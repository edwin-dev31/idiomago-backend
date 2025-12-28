package com.linguanova.idiomago.presentation.dto.wordTranslation;

import com.linguanova.idiomago.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordTranslationDTO {
	private Long id;
	private String translatedWord;
	private String translatedExample;
	private String translatedDescription;
	private String imageUrl;
	private String audioUrl;
	private LocalDateTime createdAt = LocalDateTime.now();
	private Set<UserEntity> favoritedBy = new HashSet<>();
}
