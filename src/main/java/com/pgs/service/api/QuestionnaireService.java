package com.pgs.service.api;

import com.pgs.model.Questionnaire;

import java.util.Optional;

/**
 * Created by wkloc on 2017-02-28.
 */
public interface QuestionnaireService {

    Optional<Questionnaire> getQuestionnaire(Long id);

    Long addQuestionnaire(Questionnaire questionnaire);
}
