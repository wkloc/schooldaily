package com.pgs.service.api;

import com.pgs.model.QuestionnaireInstance;

import java.util.Optional;

/**
 * Created by wkloc on 2017-03-01.
 */
public interface QuestionnaireInstanceService {

    Optional<QuestionnaireInstance> getQuestionnaireInstance(Long id);

    Long addQuestionnaireInstance(QuestionnaireInstance questionnaire);
}
