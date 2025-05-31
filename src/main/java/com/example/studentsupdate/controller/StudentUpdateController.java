package com.example.studentsupdate.controller;

import com.example.studentsupdate.model.Student;
import com.example.studentsupdate.service.StudentUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentUpdateController {

    private final StudentUpdateService service;

    public StudentUpdateController(StudentUpdateService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return service.updateStudent(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 