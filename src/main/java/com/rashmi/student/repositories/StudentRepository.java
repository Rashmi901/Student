package com.rashmi.student.repositories;

import com.rashmi.student.entities.StudentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentRecord, Long> {
}

