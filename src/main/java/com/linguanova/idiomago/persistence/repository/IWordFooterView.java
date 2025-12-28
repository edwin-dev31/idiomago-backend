package com.linguanova.idiomago.persistence.repository;

import com.linguanova.idiomago.persistence.view.WordFooterView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWordFooterView extends JpaRepository<WordFooterView, Long> {
}
