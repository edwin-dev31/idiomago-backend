package com.linguanova.idiomago.presentation.dto.wordTranslation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveMultipleWordTranslationDTO {
    private Long userId;
    private String word;
    private String languageCode;
    private Long categoryId;

    public SaveMultipleWordTranslationDTO(Long userId, String word, String languageCode, Long categoryId) {
        this.userId = userId;
        this.word = word;
        this.languageCode = languageCode;
        this.categoryId = categoryId;
    }
}
