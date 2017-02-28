package com.pgs.repository;

import com.pgs.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wkloc on 2017-02-22.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryIT extends AbstractRepositoryIT {

    @Autowired
    private StudentRepository repository;

    @Test
    public void findByIdShouldReturnStudent() {
        Student student = new Student("Jan", "Kowalski");
        this.entityManager.persist(student);

        Student persistedStudent = this.repository.findOne(student.getId());

        assertThat(student.getFirstName()).isEqualTo(persistedStudent.getFirstName());
        assertThat(student.getLastName()).isEqualTo(persistedStudent.getLastName());
    }

    @Test
    public void findByFirstNameAndLastNameShouldReturnStudent() {
        Student student = new Student("Jan", "Kowalski");
        this.entityManager.persist(student);

        List<Student> fetchStudentList = this.repository.findByFirstNameAndLastName("Jan", "Kowalski");

        assertThat(fetchStudentList.size()).isEqualTo(1);
        assertThat(student.getFirstName()).isEqualTo(fetchStudentList.get(0).getFirstName());
        assertThat(student.getLastName()).isEqualTo(fetchStudentList.get(0).getLastName());
    }

    @Test
    public void findByLastNameShouldReturnStudent() {
        Student student = new Student("Jan", "Kowalski");
        this.entityManager.persist(student);

        List<Student> fetchStudentList = this.repository.findByLastName("Kowalski");

        assertThat(fetchStudentList.size()).isEqualTo(1);
        assertThat(student.getFirstName()).isEqualTo(fetchStudentList.get(0).getFirstName());
        assertThat(student.getLastName()).isEqualTo(fetchStudentList.get(0).getLastName());
    }

}