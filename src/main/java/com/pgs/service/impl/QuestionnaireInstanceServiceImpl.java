package com.pgs.service.impl;

import com.pgs.model.Question;
import com.pgs.model.Questionnaire;
import com.pgs.model.QuestionnaireInstance;
import com.pgs.model.Users;
import com.pgs.repository.*;
import com.pgs.service.api.QuestionnaireInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * Created by wkloc on 2017-03-01.
 */
@Component("questionnaireInstanceService")
@Transactional
public class QuestionnaireInstanceServiceImpl implements QuestionnaireInstanceService {

    @Autowired
    private QuestionnaireInstanceRepository questionnaireInstanceRepository;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Optional<QuestionnaireInstance> getQuestionnaireInstance(Long id) {
        return Optional.ofNullable(questionnaireInstanceRepository.findOne(id));
    }

    @Override
    public Long addQuestionnaireInstance(QuestionnaireInstance questionnaireInstance) {
        Users author = userRepository.findOne(1L);
        Date currentDate = new Date();
        Questionnaire questionnaire = questionnaireRepository.findOne(questionnaireInstance.getQuestionnaire().getId());


        questionnaireInstance.getQuestionInstance().stream().forEach(t -> {
            Question question = questionRepository.findOne(t.getQuestion().getId());
            t.setQuestion(question);
            t.setCreatedBy(author);
            t.setCreatedOn(currentDate);
        });
        questionnaireInstance.setCreatedBy(author);
        questionnaireInstance.setCreatedOn(currentDate);

        questionnaireInstance.setQuestionnaire(questionnaire);

        Long questionaireId = questionnaireInstanceRepository.save(questionnaireInstance).getId();
        return questionaireId;
    }
}
