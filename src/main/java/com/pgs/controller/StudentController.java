package com.pgs.controller;

import com.pgs.model.Student;
import com.pgs.service.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wkloc on 2017-02-20.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentDetailsService studentDetailsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Student> getAllStudents() {
        return studentDetailsService.getAllStudents();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addStudent(Student student) {
        studentDetailsService.addStudent(student);
    }
}
