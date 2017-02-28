package com.pgs.service;

import com.google.common.collect.Lists;
import com.pgs.model.Student;
import com.pgs.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by wkloc on 2017-02-22.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentDetailsServiceTest {

    @InjectMocks
    private StudentDetailsService service;
    @Mock
    private StudentRepository repository;

    @Test
    public void shouldFindAllStudents() {
        when(repository.findAll()).thenReturn(Lists.newArrayList(
                new Student("Adam", "Smith"), new Student("John", "Smith")));

        List<Student> result = service.getAllStudents();
        assertEquals(2, result.size());
        assertEquals("Adam", result.get(0).getFirstName());
        assertEquals("Smith", result.get(0).getLastName());
        assertEquals("John", result.get(1).getFirstName());
        assertEquals("Smith", result.get(1).getLastName());
    }
}