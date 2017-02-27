package com.pgs.json;

import com.pgs.model.Student;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.json.*;
import org.springframework.test.context.junit4.*;

import static org.assertj.core.api.Assertions.*;
/**
 * Created by wkloc on 2017-02-27.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class StudentJsonTest {

    @Autowired
    private JacksonTester<Student> json;

    @Test
    public void testSerialize() throws Exception {
        Student student = new Student("Jan", "Kowalski");
        // Assert against a `.json` file in the same package as the test
        assertThat(this.json.write(student)).isEqualToJson("{\"firstName\":\"Jan\",\"lastName\":\"Kowalski\"}");
        // Or use JSON path based assertions
        assertThat(this.json.write(student)).hasJsonPathStringValue("@.firstName");
        assertThat(this.json.write(student)).extractingJsonPathStringValue("@.firstName")
                .isEqualTo("Jan");
        assertThat(this.json.write(student)).hasJsonPathStringValue("@.lastName");
        assertThat(this.json.write(student)).extractingJsonPathStringValue("@.lastName")
                .isEqualTo("Kowalski");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"firstName\":\"Jan\",\"lastName\":\"Kowalski\"}";
        assertThat(this.json.parse(content))
                .isEqualTo(new Student("Jan", "Kowalski"));
        assertThat(this.json.parseObject(content).getFirstName()).isEqualTo("Jan");
        assertThat(this.json.parseObject(content).getLastName()).isEqualTo("Kowalski");
    }
}
