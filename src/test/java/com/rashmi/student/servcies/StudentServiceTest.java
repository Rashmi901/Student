package com.rashmi.student.servcies;

import com.rashmi.student.entities.StudentRecord;
import com.rashmi.student.models.Student;
import com.rashmi.student.repositories.StudentRepository;
import com.rashmi.student.services.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository studentRepository;
    private StudentRecord expectedStudent;
    private StudentServiceImpl studentService;
    private StudentRecord expectedRecord;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        studentService = new StudentServiceImpl(studentRepository);
        expectedStudent = new StudentRecord("Rashmi", "BE", "VTU");
        expectedRecord = new StudentRecord("Rashmi", "BE", "VTU");
    }

    @Test
    void getAll() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(
                expectedStudent));
        List<Student> allStudents = studentService.getAll();
        assertEquals(1, allStudents.size());
        Student student = allStudents.get(0);
        assertEquals(expectedStudent.getName(), student.getName());
        assertEquals(expectedStudent.getSubject(), student.getSubject());
        assertEquals(expectedStudent.getUniversity(), student.getUniversity());
    }

    @Test
    void save() {
        Long expectedId = 134134L;
        ReflectionTestUtils.setField(expectedStudent, "id", expectedId);
        when(studentRepository.save(argThat(record -> {
            assertEquals("Rashmi", record.getName());
            assertEquals("BE", record.getSubject());
            assertEquals("VTU", record.getUniversity());
            return true;
        }))).thenReturn(expectedStudent);
        Long id = studentService.save(new Student("Rashmi", "BE", "VTU"));
        assertEquals(expectedId, id);
    }

    @Test
    void deleteSuccess() {
        when(studentRepository.findById(101L)).thenReturn(Optional.of(expectedRecord));
        boolean result = studentService.delete(101L);
        assertTrue(result);
        verify(studentRepository, times(1)).delete(expectedRecord);
    }

    @Test
    void deleteFailure() {
        when(studentRepository.findById(101L)).thenReturn(Optional.empty());
        boolean result = studentService.delete(101L);
        assertFalse(result);
        verify(studentRepository, times(0)).delete(any(StudentRecord.class));
    }

     @Test
    void getStudentSuccess() {
        when(studentRepository.findById(101L)).thenReturn(Optional.of(expectedRecord));
        Student result = studentService.getStudentById(101L).orElseThrow(() -> new RuntimeException("Contact result expected"));
        assertEquals("Rashmi",result.getName());
        assertEquals("BE",result.getSubject());
        assertEquals("VTU",result.getUniversity());

    }
    @Test
    void getStudentNotFound() {
        when(studentRepository.findById(101L)).thenReturn(Optional.empty());
        Optional<Student> result = studentService.getStudentById(101L);
        assertEquals(Optional.empty(), result);
    }


}