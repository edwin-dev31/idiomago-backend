package com.linguanova.idiomago.presentation.dto.wordTranslation;

import com.linguanova.idiomago.util.Visibility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveSingleWordTranslationDTO {
    private Long user;
    private String word;
    private String languageCode;
    private Long categoryId;
    private String description;
    private String example;
    private Visibility visibility;
}
