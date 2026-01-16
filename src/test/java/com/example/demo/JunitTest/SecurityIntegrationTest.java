package com.example.demo.JunitTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Model.Student;
import com.example.demo.Repo.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("🛡️ Professional RBAC System Tests")
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    private Long savedStudentId;

    @BeforeEach
    void setup() {
        // DB saaf karke fresh entry insert karna (Standard Practice)
        studentRepository.deleteAll(); 
        Student s = studentRepository.save(new Student(null, "Test Student", "Java"));
        savedStudentId = s.getId(); 
    }

    // --- 1. AUTHENTICATION MODULE ---

    @Test
    @DisplayName("✅ SUCCESS: Valid Login Scenario (Checks Message & Role)")
    void testLoginSuccess() throws Exception {
        String loginJson = "{\"username\":\"user\",\"password\":\"user123\"}";
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("LOGIN_SUCCESS"))
                .andExpect(jsonPath("$.role").value("ROLE_USER")); 
    }

    @Test
    @DisplayName("❌ FAILURE: Invalid Credentials")
    void testLoginFailure() throws Exception {
        String wrongJson = "{\"username\":\"fake\",\"password\":\"fake\"}";
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(wrongJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("INVALID_CREDENTIALS"));
    }

    @Test
    @DisplayName("🛑 REJECTED: Unauthenticated User cannot access APIs")
    void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/student/getAllStudent"))
                .andExpect(status().isUnauthorized());
    }

    // --- 2. STUDENT READ OPERATIONS ---

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("📖 ALLOWED: USER Role can Fetch All Students")
    void testGetAllStudentsUser() throws Exception {
        mockMvc.perform(get("/student/getAllStudent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Students fetched successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("📖 ALLOWED: ADMIN Role can Fetch All Students")
    void testGetAllStudentsAdmin() throws Exception {
        mockMvc.perform(get("/student/getAllStudent"))
                .andExpect(status().isOk());
    }

    // --- 3. STUDENT WRITE/UPDATE OPERATIONS ---

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("➕ ALLOWED: ADMIN Role can Add a New Student")
    void testAddStudentAdmin() throws Exception {
        Student s = new Student(null, "Rahul", "Spring Boot");
        mockMvc.perform(post("/student/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Student added successfully"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("🚫 FORBIDDEN: USER Role cannot Add a Student")
    void testAddStudentUserForbidden() throws Exception {
        mockMvc.perform(post("/student/addStudent").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("🔄 ALLOWED: ADMIN Role can Update a Student")
    void testUpdateStudentAdmin() throws Exception {
        Student updated = new Student(savedStudentId, "Updated Name", "Java FullStack");
        mockMvc.perform(put("/student/updateStudent/" + savedStudentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("🚫 FORBIDDEN: USER Role cannot Update a Student")
    void testUpdateStudentUserForbidden() throws Exception {
        mockMvc.perform(put("/student/updateStudent/" + savedStudentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Malicious Update\"}"))
                .andExpect(status().isForbidden());
    }

    // --- 4. STUDENT DELETE OPERATIONS ---

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("🗑️ ALLOWED: ADMIN Role can Delete a Student")
    void testDeleteStudentAdmin() throws Exception {
        mockMvc.perform(delete("/student/deleteStudent/" + savedStudentId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("🚫 FORBIDDEN: USER Role cannot Delete a Student")
    void testDeleteStudentUserForbidden() throws Exception {
        mockMvc.perform(delete("/student/deleteStudent/" + savedStudentId))
                .andExpect(status().isForbidden());
    }
}