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

import java.util.HashMap;
import java.util.Map;

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
        int id = 0;

        //ADD
        HttpEntity<Student> requestPOST = new HttpEntity<>(student, headers);
        ResponseEntity responsePOST = restTemplate.postForEntity(URL + "student", requestPOST, String.class);
        assertTrue(responsePOST.getStatusCode().is2xxSuccessful());
        id = Integer.valueOf(responsePOST.getBody().toString()).intValue();
        assertTrue(id > 0);

        //CHECK
        Map<String, String> map = new HashMap();
        map.put("id", String.valueOf(id));
        ResponseEntity<Student> responseGET1 = restTemplate.getForEntity(
                URL + "student/{id}", Student.class, map);
        assertTrue(responseGET1.getStatusCode().is2xxSuccessful());
        assertEquals("Jan", responseGET1.getBody().getFirstName());
        assertEquals("Kowalski", responseGET1.getBody().getLastName());

        //UPDATE
        student.setId(id);
        student.setFirstName("Janusz");
        HttpEntity<Student> requestPUT = new HttpEntity<>(student, headers);
        restTemplate.put(URL + "student", requestPUT);

        //CHECK
        ResponseEntity<Student> responseGET2 = restTemplate.getForEntity(
                URL + "student/{id}", Student.class, map);
        assertTrue(responseGET2.getStatusCode().is2xxSuccessful());
        assertEquals("Janusz", responseGET2.getBody().getFirstName());
        assertEquals("Kowalski", responseGET2.getBody().getLastName());

        //DELETE
        restTemplate.delete(URL + "student/{id}", map);

        //CHECK
        ResponseEntity<Student> responseGET3 = restTemplate.getForEntity(
                URL + "student/{id}", Student.class, map);
        assertTrue(responseGET3.getStatusCode().is4xxClientError());
        assertNull(responseGET3.getBody().getId());
        assertNull(responseGET3.getBody().getFirstName());
        assertNull(responseGET3.getBody().getLastName());
    }
}