package com.pgs.service;

import com.pgs.model.Student;
import com.pgs.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wkloc on 2017-02-20.
 */
@Component("studentDetailsService")
@Transactional
public class StudentDetailsService {

    private final Logger log = LoggerFactory.getLogger(StudentDetailsService.class);

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }
}
