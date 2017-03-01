package com.pgs.repository;

import com.pgs.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wkloc on 2017-02-28.
 */
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
}