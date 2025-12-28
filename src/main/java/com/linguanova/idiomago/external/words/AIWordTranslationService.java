package com.linguanova.idiomago.external.words;

import com.linguanova.idiomago.external.images.UnsplashService;
import com.linguanova.idiomago.persistence.entity.UserEntity;
import com.linguanova.idiomago.persistence.entity.WordTranslationEntity;
import com.linguanova.idiomago.persistence.repository.IUserRepository;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveMultipleWordTranslationDTO;
import com.linguanova.idiomago.presentation.dto.wordTranslation.SaveSingleWordTranslationDTO;
import com.linguanova.idiomago.service.impl.WordTranslationService;
import com.linguanova.idiomago.util.Visibility;
import com.linguanova.idiomago.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIWordTranslationService {

    private final OpenAIService openAIService;
    private final IUserRepository userRepository;
    private final WordTranslationService wordTranslationService;

    @Autowired
    public AIWordTranslationService(OpenAIService openAIService,
                                    IUserRepository userRepository,
                                    WordTranslationService wordTranslationService) {
        this.openAIService = openAIService;
        this.userRepository = userRepository;
        this.wordTranslationService = wordTranslationService;
    }

    public UserEntity getOrThrowUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    public WordTranslationEntity saveMultipleWordsIA(SaveMultipleWordTranslationDTO dto) {

        UserEntity user = getOrThrowUser(dto.getUserId());
        IAResponse response = openAIService.getWordExplanation(dto.getWord(), dto.getLanguageCode());
        String imageUrl = UnsplashService.getImageUrlForWord(dto.getWord());

        return wordTranslationService.saveTranslationFull(
                user,  dto.getWord(), response.getWord(), response.getExample(), response.getDescription(),
                dto.getLanguageCode(), dto.getCategoryId(), imageUrl, Visibility.PUBLIC);
    }

    public WordTranslationEntity saveSingleWords(SaveSingleWordTranslationDTO dto) {
        UserEntity user = getOrThrowUser(dto.getUser());
        String imageUrl = UnsplashService.getImageUrlForWord(dto.getWord());

        return wordTranslationService.saveTranslationFull(
                user, dto.getWord(), dto.getWord(), dto.getExample(), dto.getDescription(),
                dto.getLanguageCode(), dto.getCategoryId(), imageUrl, dto.getVisibility()
        );
    }
}
