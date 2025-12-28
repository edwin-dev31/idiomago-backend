package com.linguanova.idiomago.presentation.dto.wordTranslation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateMultipleWordTranslationDTO {
    private Long userId;
    private String word;
    private Long categoryId;
    private List<String> languageCodes;

}
