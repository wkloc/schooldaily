package com.pgs.controller;

import com.pgs.model.QuestionnaireInstance;
import com.pgs.service.api.QuestionnaireInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

/**
 * Created by wkloc on 2017-03-01.
 */
@RestController
@RequestMapping("/questionnaireInstance")
public class QuestionnaireInstanceController {
    @Autowired
    private QuestionnaireInstanceService questionnaireInstanceService;

    @RequestMapping(value = "/instance", method = RequestMethod.POST)
    public Long addQuestionnaireInstance(@RequestBody QuestionnaireInstance questionnaireInstance) {
        return questionnaireInstanceService.addQuestionnaireInstance(questionnaireInstance);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/instance/{id}")
    public ResponseEntity<QuestionnaireInstance> getQuestionnaireInstance(@PathVariable Long id) {
        return ResponseEntity.ok(questionnaireInstanceService.getQuestionnaireInstance(id)
                .orElseThrow(() ->  new EntityNotFoundException("Questionnaire instance not found!")));
    }
}
