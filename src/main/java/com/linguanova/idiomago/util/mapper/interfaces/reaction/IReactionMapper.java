package com.linguanova.idiomago.util.mapper.interfaces.reaction;

import com.linguanova.idiomago.persistence.entity.ReactionEntity;
import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.presentation.dto.reaction.CreateReactionDTO;
import com.linguanova.idiomago.presentation.dto.reaction.ReactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IReactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "wordTranslation", source = "wordTranslation")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    ReactionEntity mapFrom(CreateReactionDTO dto, UserEntity user, WordTranslationEntity wordTranslation);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "wordTranslationId", source = "wordTranslation.id")
    ReactionDTO mapTo(ReactionEntity entity);
}
