package com.linguanova.idiomago.persistence.view;

import com.linguanova.idiomago.util.Visibility;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Getter
@Setter
@Immutable
@Table(name = "view_translations")
public class TranslationView {
	@Id
	private Long wordTranslationId;
	private Long userId;
	private Long wordId;
	private String originalWord;

	private Long languageId;
	private String languageName;
	private String languageCode;

	private Long categoryId;
	private String categoryName;

	private String translatedWord;
	private String translatedExample;
	private String translatedDescription;
	private String imageUrl;
	private String audioUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Visibility visibility;
	private LocalDateTime createdAt;

}

