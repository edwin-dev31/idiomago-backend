package com.linguanova.idiomago.persistence.view;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Immutable
@Table(name = "view_word_footer_data")
public class WordFooterView {

    @Id
    @Column(name = "word_translation_id")
    private Long wordTranslationId;

    private String username;
    private String imageUrl;
    private String translatedWord;
    private String languageName;
    private LocalDateTime createdAt;

    private Integer reactionLike;
    private Integer reactionDislike;
    private Integer reactionLove;
    private Integer reactionFire;
    private Integer reactionPerfect;
}
