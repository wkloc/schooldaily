package com.pgs.service;

import com.pgs.model.Student;
import com.pgs.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by wkloc on 2017-02-20.
 */
@Component("studentDetailsService")
@Transactional
public class StudentDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(StudentDetailsService.class);

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudent(Integer id) {
        return Optional.ofNullable(studentRepository.findOne(id));
    }

    public Integer addStudent(Student student) {
        return studentRepository.save(student).getId();
    }

    public Optional<Student> updateStudent(Student student) {
        return Optional.ofNullable(studentRepository.save(student));
    }

    public void deleteStudent(Integer id) {
        studentRepository.delete(id);
    }
}
