package com.example.studentsupdate.service;

import com.example.studentsupdate.model.Student;
import com.example.studentsupdate.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentUpdateServiceTest {

    @Mock
    private StudentRepository repository;

    private StudentUpdateService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new StudentUpdateService(repository);
    }

    @Test
    void updateStudent_Success() {
        // Arrange
        Long id = 1L;
        Student existingStudent = new Student("Old Name", "old@email.com", 20);
        Student updatedStudent = new Student("New Name", "new@email.com", 25);
        
        when(repository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(repository.findByEmail("new@email.com")).thenReturn(Optional.empty());
        when(repository.save(any(Student.class))).thenReturn(updatedStudent);

        // Act
        Optional<Student> result = service.updateStudent(id, updatedStudent);

        // Assert
        assertTrue(result.isPresent());
        Student student = result.get();
        assertEquals("New Name", student.getName());
        assertEquals("new@email.com", student.getEmail());
        assertEquals(25, student.getAge());
        verify(repository).findById(id);
        verify(repository).findByEmail("new@email.com");
        verify(repository).save(any(Student.class));
    }

    @Test
    void updateStudent_NotFound() {
        // Arrange
        Long id = 1L;
        Student updatedStudent = new Student("New Name", "new@email.com", 25);
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Student> result = service.updateStudent(id, updatedStudent);

        // Assert
        assertFalse(result.isPresent());
        verify(repository).findById(id);
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    void updateStudent_DuplicateEmail() {
        // Arrange
        Long id = 1L;
        Student existingStudent = new Student("Old Name", "old@email.com", 20);
        Student updatedStudent = new Student("New Name", "existing@email.com", 25);
        Student otherStudent = new Student("Other Name", "existing@email.com", 30);
        
        when(repository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(repository.findByEmail("existing@email.com")).thenReturn(Optional.of(otherStudent));

        // Act
        Optional<Student> result = service.updateStudent(id, updatedStudent);

        // Assert
        assertTrue(result.isPresent());
        assertNull(result.get());
        verify(repository).findById(id);
        verify(repository).findByEmail("existing@email.com");
        verify(repository, never()).save(any(Student.class));
    }
} 