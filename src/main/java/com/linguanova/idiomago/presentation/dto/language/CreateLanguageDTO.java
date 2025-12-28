package com.linguanova.idiomago.presentation.dto.language;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateLanguageDTO {
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Code is required")
	private String code;
}
