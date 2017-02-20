package com.pgs.repository;

import com.pgs.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wkloc on 2017-02-20.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

}
