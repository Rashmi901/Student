package com.rashmi.student.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity(name = "Students")
@Getter
@NoArgsConstructor
public class StudentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String university;

    public StudentRecord(String name, String subject, String university) {
        this.name = name;
        this.subject = subject;
        this.university = university;
    }
}