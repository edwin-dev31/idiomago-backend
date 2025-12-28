package com.linguanova.idiomago.persistence.repository;

import com.linguanova.idiomago.persistence.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILanguageRepository extends JpaRepository<LanguageEntity, Long> {
    LanguageEntity findByCode(String code);

}
