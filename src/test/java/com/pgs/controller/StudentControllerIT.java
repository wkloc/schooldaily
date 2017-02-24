package com.pgs.controller;

import com.pgs.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by wkloc on 2017-02-21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StudentControllerIT extends AbstractControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:controllerDataSet/student_controller.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:controllerDataSet/student_controller_rollback.sql")
    })
    public void shouldGetStudent() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(
                URL + "student/1", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody().toString(), "{\"id\":1,\"firstName\":\"Jan\",\"lastName\":\"Kowalski\"}");
    }

    @Test
    public void shouldNotGetStudent() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(
                URL + "student/1", String.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.getBody().toString().contains("Student not found"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:controllerDataSet/student_controller.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:controllerDataSet/student_controller_rollback.sql")
    })
    public void shouldGetAllStudents() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(
                URL + "student", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody().toString(),
                "[{\"id\":1,\"firstName\":\"Jan\",\"lastName\":\"Kowalski\"},{\"id\":2,\"firstName\":\"Adam\",\"lastName\":\"Jarosz\"},{\"id\":3,\"firstName\":\"Michal\",\"lastName\":\"Nowak\"}]");
    }

    @Test
    public void shouldAddUpdateDeleteStudent() throws Exception {

        Student student = new Student("Jan", "Kowalski");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //ADD
        HttpEntity<Student> requestPOST = new HttpEntity<>(student, headers);
        ResponseEntity responsePOST = restTemplate.postForEntity(URL + "student", requestPOST, String.class);
        assertTrue(responsePOST.getStatusCode().is2xxSuccessful());
        assertTrue(Integer.valueOf(responsePOST.getBody().toString()).intValue() > 0);

        responsePOST = restTemplate.getForEntity(
                URL + "student/{id}", String.class, responsePOST.getBody().toString());
        assertTrue(responsePOST.getStatusCode().is2xxSuccessful());
        assertEquals("{\"id\":1,\"firstName\":\"Jan\",\"lastName\":\"Kowalski\"}", responsePOST.getBody().toString());

        //CHECK
        ResponseEntity responseGET = restTemplate.getForEntity(
                URL + "student/1", String.class);
        assertTrue(responseGET.getStatusCode().is2xxSuccessful());
        assertEquals("{\"id\":1,\"firstName\":\"Jan\",\"lastName\":\"Kowalski\"}", responseGET.getBody().toString());

        //UPDATE
        student.setId(1);
        student.setFirstName("Janusz");
        HttpEntity<Student> requestPUT = new HttpEntity<>(student, headers);
        restTemplate.put(URL + "student", requestPUT);

        //CHECK
        responseGET = restTemplate.getForEntity(
                URL + "student/1", String.class);
        assertTrue(responseGET.getStatusCode().is2xxSuccessful());
        assertEquals("{\"id\":1,\"firstName\":\"Janusz\",\"lastName\":\"Kowalski\"}", responseGET.getBody().toString());

        //DELETE
        restTemplate.delete(URL + "student/1");

        //CHECK
        responseGET = restTemplate.getForEntity(
                URL + "student/1", String.class);
        assertTrue(responseGET.getStatusCode().is4xxClientError());
        assertTrue(responseGET.getBody().toString().contains("Student not found"));
    }
}