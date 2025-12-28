package com.linguanova.idiomago.persistence.entity;

import com.linguanova.idiomago.util.Visibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "word_translation")
public class WordTranslationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private UserEntity user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "word_id")
	private WordEntity word;

	@ManyToOne(optional = false)
	@JoinColumn(name = "language_id")
	private LanguageEntity language;

	@ManyToOne(optional = false)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;

	@Column(nullable = false)
	private String translatedWord;

	private String translatedExample;
	private String translatedDescription;
	private String imageUrl;
	private String audioUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Visibility visibility;

	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToMany(mappedBy = "favorites")
	private Set<UserEntity> favoritedBy = new HashSet<>();
}