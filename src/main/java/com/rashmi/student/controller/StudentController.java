package com.rashmi.student.controller;

import com.rashmi.student.models.Student;
import com.rashmi.student.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;


@RequestMapping("/students")
@RestController
public class StudentController {
    private StudentService service;

    public StudentController(StudentService service) {

        this.service = service;
    }

    @GetMapping
    List<Student> getAllStudents() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody @Valid Student student, HttpServletRequest request) throws URISyntaxException {
        Long id = service.save(student);
        return ResponseEntity.created(new URI(request.getRequestURL() + "/" + id)).body(student);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id){
        if(service.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<Student> getStudentById(@PathVariable long id){
        return service.getStudentById(id).map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build());
    }
}

