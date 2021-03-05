package com.rashmi.student.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.rashmi.student.models.Student;
import com.rashmi.student.services.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

    class StudentControllerTest {

        private StudentController studentController;
        private StudentServiceImpl studentService;
        private Student student;

        @BeforeEach
        void setUp() {
            studentService = mock(StudentServiceImpl.class);
            studentController = new StudentController(studentService);
            student = new Student("Rashmi","BE","VTU");

        }

        @Test
        void getAllStudents() {
            Student student = new Student("Rashmi", "BE", "VTU");
            when(studentService.getAll()).thenReturn(Arrays.asList(student));
            StudentController controller = new StudentController(studentService);
            List<Student> allStudents = controller.getAllStudents();
            assertEquals(1, allStudents.size());
            assertSame(student, allStudents.get(0));
        }
        @Test
        void saveStudent() throws URISyntaxException {
            when(studentService.save(student)).thenReturn(101L);
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getRequestURL()).thenReturn(new StringBuffer("http://rashmi.com/students"));
            ResponseEntity<Student> result = studentController.saveStudent(student, request);
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(new URI("http://rashmi.com/students/101"),result.getHeaders().getLocation());
            assertSame(student, result.getBody());

        }

        @Test
        void deleteStudentSuccess() {
            when(studentService.delete(101L)).thenReturn(true);
            ResponseEntity<Void> result = studentController.deleteStudent(101L);
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        }
        @Test
        void deleteStudentNotFound() {
            when(studentService.delete(101L)).thenReturn(false);
            ResponseEntity<Void> result = studentController.deleteStudent(101L);
            assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        }

        @Test
        void getStudentSuccess() {
            when(studentService.getStudentById(101L)).thenReturn(Optional.of(student));
            ResponseEntity<Student> result = studentController.getStudentById(101L);
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertSame(student, result.getBody());
        }

        @Test
        void getStudentNotFound() {
            when(studentService.getStudentById(101L)).thenReturn(Optional.empty());
            ResponseEntity<Student> result = studentController.getStudentById(101L);
            assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        }
    }
