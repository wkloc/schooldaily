package com.pgs.controller;

import com.pgs.model.Student;
import com.pgs.service.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by wkloc on 2017-02-20.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentDetailsService studentDetailsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Student> getAllStudents() {
        return studentDetailsService.getAllStudents();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer id) {
        return ResponseEntity.ok(studentDetailsService.getStudent(id)
                .orElseThrow(() ->  new EntityNotFoundException("Student not found!")));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Integer addStudent(@RequestBody Student student) {
        return studentDetailsService.addStudent(student);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Student>  updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentDetailsService.updateStudent(student)
                .orElseThrow(() -> new EntityNotFoundException("Student not found!")));

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentDetailsService.deleteStudent(id);
    }
}
