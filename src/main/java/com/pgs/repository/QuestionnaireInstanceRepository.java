package com.pgs.repository;

import com.pgs.model.QuestionnaireInstance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wkloc on 2017-03-01.
 */
public interface QuestionnaireInstanceRepository extends JpaRepository<QuestionnaireInstance, Long> {
}
