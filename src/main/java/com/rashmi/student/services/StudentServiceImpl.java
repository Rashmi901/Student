package com.rashmi.student.services;

import com.rashmi.student.entities.StudentRecord;
import com.rashmi.student.models.Student;
import com.rashmi.student.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

        private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
       this.repository = repository;
    }

    @Override
    public List<Student> getAll() {
        return repository.findAll().stream().map(record ->
                new Student(record.getName(),record.getSubject(),record.getUniversity())).collect(Collectors.toList());
    }

    @Override
    public Long save(Student student) {
        StudentRecord studentRecord = new StudentRecord(student.getName(),student.getSubject(),student.getUniversity());
        return repository.save(studentRecord).getId();
    }

    @Override
    public boolean delete(long id) {
        return repository.findById(id).map(record -> {
            repository.delete(record);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<Student> getStudentById(long id) {
        return repository.findById(id).map(record -> new Student(record.getName(),record.getSubject(),record.getUniversity())
                );
    }
}
