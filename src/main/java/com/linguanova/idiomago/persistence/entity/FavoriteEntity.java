package com.linguanova.idiomago.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "favorites")
public class FavoriteEntity {

	@EmbeddedId
	private FavoriteId id;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne
	@MapsId("wordTranslationId")
	@JoinColumn(name = "word_translation_id")
	private WordTranslationEntity wordTranslation;

	private LocalDateTime createdAt = LocalDateTime.now();

}
