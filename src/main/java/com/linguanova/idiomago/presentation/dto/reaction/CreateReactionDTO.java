package com.linguanova.idiomago.presentation.dto.reaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateReactionDTO {

    private Long userId;
    private Long wordTranslationId;
    private String emoji;
}
