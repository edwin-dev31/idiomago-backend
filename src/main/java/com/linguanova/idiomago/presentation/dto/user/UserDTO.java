package com.linguanova.idiomago.presentation.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private Long id;
	private String username;
	private String password;
	private String email;
	private Boolean verified;
	private String imageUrl;
	private String imagePublicId;
	private LocalDateTime createdAt;
}
