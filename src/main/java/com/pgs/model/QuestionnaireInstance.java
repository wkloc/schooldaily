package com.pgs.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by wkloc on 2017-02-28.
 */
@Entity
public class QuestionnaireInstance extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="questionnaire_id")
    private Questionnaire questionnaire;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_instance_id", nullable = false)
    private Set<QuestionInstance> questionInstance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Set<QuestionInstance> getQuestionInstance() {
        return questionInstance;
    }

    public void setQuestionInstance(Set<QuestionInstance> questionInstance) {
        this.questionInstance = questionInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionnaireInstance that = (QuestionnaireInstance) o;

        if (!id.equals(that.id)) return false;
        if (!questionnaire.equals(that.questionnaire)) return false;
        return questionInstance != null ? questionInstance.equals(that.questionInstance) : that.questionInstance == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + questionnaire.hashCode();
        result = 31 * result + (questionInstance != null ? questionInstance.hashCode() : 0);
        return result;
    }
}
