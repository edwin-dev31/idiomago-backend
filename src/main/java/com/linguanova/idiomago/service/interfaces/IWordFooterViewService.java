package com.linguanova.idiomago.service.interfaces;

import com.linguanova.idiomago.persistence.view.WordFooterView;

import java.util.List;
import java.util.Optional;

public interface IWordFooterViewService {
    List<WordFooterView> findAll();
    Optional<WordFooterView> findById(Long id);
}
