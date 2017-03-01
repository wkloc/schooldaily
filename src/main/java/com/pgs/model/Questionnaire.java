package com.pgs.model;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by wkloc on 2017-02-27.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"questionnaire_name"})})
public class Questionnaire extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable=false)
    private Long id;

    @Column(name = "questionnaire_name")
    private String questionnaireName;

    private String questionnaireDescription;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Set<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Questionnaire that = (Questionnaire) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (questionnaireName != null ? !questionnaireName.equals(that.questionnaireName) : that.questionnaireName != null)
            return false;
        if (questionnaireDescription != null ? !questionnaireDescription.equals(that.questionnaireDescription) : that.questionnaireDescription != null)
            return false;
        return questions != null ? questions.equals(that.questions) : that.questions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (questionnaireName != null ? questionnaireName.hashCode() : 0);
        result = 31 * result + (questionnaireDescription != null ? questionnaireDescription.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionaireName) {
        this.questionnaireName = questionaireName;
    }

    public String getQuestionnaireDescription() {
        return questionnaireDescription;
    }

    public void setQuestionnaireDescription(String questionaireDescription) {
        this.questionnaireDescription = questionaireDescription;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

}
