package com.linguanova.idiomago.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reaction")
public class ReactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "word_translation_id", nullable = false)
    private WordTranslationEntity wordTranslation;

    @Column(nullable = false, length = 10)
    private String emoji;

    private LocalDateTime createdAt = LocalDateTime.now();
}
