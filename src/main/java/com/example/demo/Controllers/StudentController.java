package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Student;
import com.example.demo.Services.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	private final StudentService service;

	public StudentController(StudentService service) {
		this.service = service;
	}

	
	@GetMapping("/getAllStudent")
	public ResponseEntity<Map<String, Object>> getAllStudents() {
	    List<Student> students = service.getAllStudents();

	    Map<String, Object> response = new HashMap<>();
	    response.put("code", 200);
	    response.put("message", "Students fetched successfully");
	    response.put("data", students);

	    return ResponseEntity.ok(response);
	}


	@PostMapping("/addStudent")
	public ResponseEntity<Map<String, Object>> addStudent(@RequestBody Student student) {
	    Student savedStudent = service.saveStudent(student);

	    Map<String, Object> response = new HashMap<>();
	    response.put("code", 200);
	    response.put("message", "Student added successfully");
	    response.put("data", savedStudent);

	    return ResponseEntity.ok(response);
	}


	// 1. Update Student
	@PutMapping("/updateStudent/{id}")
	public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
		return service.updateStudent(id, studentDetails);
	}

	// 2. Delete Student
	@DeleteMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable Long id) {
		service.deleteStudent(id);
		return "Student deleted successfully with id: " + id;
	}
}