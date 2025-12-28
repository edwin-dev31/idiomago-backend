package com.linguanova.idiomago.presentation.dto.wordTranslation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateWordTranslationDTO {
	private Long wordId;
	private Long languageId;
	private String translatedWord;
	private String translatedExample;
	private String translatedDescription;
	private String imageUrl;
	private String audioUrl;
}
