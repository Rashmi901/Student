package com.rashmi.student.services;

import com.rashmi.student.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAll();

    Long save(Student student);

    boolean delete(long id);

    Optional<Student> getStudentById(long id);

}

