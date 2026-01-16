package com.example.demo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Student;
import com.example.demo.Repo.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    // 1. Update Student Logic
    public Student updateStudent(Long id, Student studentDetails) 
    {
        // Pehle check karo ki student exist karta hai ya nahi
    	
        return repo.findById(id).map(student -> {
            student.setName(studentDetails.getName()); // Maan lete hain Name field hai
            return repo.save(student);
            
            
        }).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // 2. Delete Student Logic
    public void deleteStudent(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }
}