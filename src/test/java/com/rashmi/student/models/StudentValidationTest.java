package com.rashmi.student.models;

import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentValidationTest {

    private Validator validator;
    private Student validStudent;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        validStudent = new Student("Rashmi", "BE","VTU");
    }

    @Test
    @Order(1)
    void validContact() {
        Set<ConstraintViolation<Student>> constraints = validator.validate(validStudent);
        assertEquals(0, constraints.size());
    }

    @Test
    @Order(2)
    void invalidValues() {
        validStudent.setName("R");
        validStudent.setSubject("B");
        validStudent.setUniversity("VTU UNIVERSITY");
        assertCommonErrors(validStudent);
    }

    @Test
    @Order(3)
    void invalidUniversityLength() {
        validStudent.setUniversity("vtuuuuuuuuuuuuuuuuuuuuuuuuuu");
        Set<ConstraintViolation<Student>> constraints = validator.validate(validStudent);
        assertEquals(1, constraints.size());
        assertEquals("University should be between 2 and 10 characters.",constraints.iterator().next().getMessage());
    }

    @Test
    @Order(4)
    void nullValues() {
        assertCommonErrors(new Student());
    }

    private void assertCommonErrors(Student student) {
        Set<ConstraintViolation<Student>> constraints = validator.validate(student);
        assertEquals(3, constraints.size());
        Map<String, String> violations = constraints.stream().collect(Collectors.toMap(f ->
                f.getPropertyPath().toString(), ConstraintViolation::getMessage));
        assertEquals("Name should be between 2 and 50 characters.",violations.get("name"));
        assertEquals("Subject should be between 2 and 5 characters.",violations.get("subject"));
        assertEquals("University should be between 2 and 10 characters.",violations.get("university"));
    }
}