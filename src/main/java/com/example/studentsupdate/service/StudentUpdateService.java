package com.example.studentsupdate.service;

import com.example.studentsupdate.model.Student;
import com.example.studentsupdate.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class StudentUpdateService {

    private final StudentRepository repository;

    public StudentUpdateService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<Student> updateStudent(Long id, Student updatedStudent) {
        return repository.findById(id).map(existingStudent -> {
            // Verificar si el nuevo email ya existe en otro estudiante
            if (!existingStudent.getEmail().equals(updatedStudent.getEmail())) {
                Optional<Student> studentWithEmail = repository.findByEmail(updatedStudent.getEmail());
                if (studentWithEmail.isPresent()) {
                    return null;
                }
            }
            
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setAge(updatedStudent.getAge());
            return repository.save(existingStudent);
        });
    }
} 