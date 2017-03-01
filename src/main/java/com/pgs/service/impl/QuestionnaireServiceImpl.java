package com.pgs.service.impl;

import com.pgs.model.Questionnaire;
import com.pgs.model.Users;
import com.pgs.repository.QuestionRepository;
import com.pgs.repository.QuestionnaireRepository;
import com.pgs.repository.UserRepository;
import com.pgs.service.api.QuestionnaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * Created by wkloc on 2017-02-28.
 */
@Component("questionnaireService")
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);

    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;



    @Override
    public Optional<Questionnaire> getQuestionnaire(Long id) {
        return Optional.ofNullable(questionnaireRepository.findOne(id));
    }

    @Override
    public Long addQuestionnaire(Questionnaire questionnaire) {
        Users author = userRepository.findOne(1L);
        Date currentDate = new Date();

        questionnaire.getQuestions().stream().forEach(t -> {
            t.setCreatedBy(author);
            t.setCreatedOn(currentDate);
        });
        questionnaire.setCreatedBy(author);
        questionnaire.setCreatedOn(currentDate);

        Long questionaireId = questionnaireRepository.save(questionnaire).getId();
        return questionaireId;
    }
}
