package com.linguanova.idiomago.presentation.dto.reaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReactionDTO {
    private Long id;
    private Long userId;
    private Long wordTranslationId;
    private String emoji;
    private LocalDateTime createdAt;
}
