package com.pgs.controller;

import com.pgs.model.Questionnaire;
import com.pgs.model.QuestionnaireInstance;
import com.pgs.service.api.QuestionnaireInstanceService;
import com.pgs.service.api.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

/**
 * Created by wkloc on 2017-02-28.
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @RequestMapping(method = RequestMethod.POST)
    public Long addQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return questionnaireService.addQuestionnaire(questionnaire);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Questionnaire> getQuestionnaire(@PathVariable Long id) {
        return ResponseEntity.ok(questionnaireService.getQuestionnaire(id)
                .orElseThrow(() ->  new EntityNotFoundException("Questionnaire not found!")));
    }


}
