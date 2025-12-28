package com.linguanova.idiomago.presentation.dto.word;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class WordDTO {
	private Long id;
	private String name;
	private LocalDateTime createdAt = LocalDateTime.now();
}
