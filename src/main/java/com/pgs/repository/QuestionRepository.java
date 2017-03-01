package com.pgs.repository;

import com.pgs.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wkloc on 2017-02-28.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}