package com.pgs.repository;

import com.pgs.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wkloc on 2017-02-20.
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByLastName(String lastname);

    List<Student> findByFirstName(String firstname);

    List<Student> findByFirstNameAndLastName(String firstname, String lastname);
}
