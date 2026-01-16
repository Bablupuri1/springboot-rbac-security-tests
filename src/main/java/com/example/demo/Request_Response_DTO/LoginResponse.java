package com.example.demo.Request_Response_DTO;
public class LoginResponse {
    private int code;
    private String message;
    private String role;

    public LoginResponse(int code, String message, String role) {
        this.code = code;
        this.message = message;
        this.role = role;
    }

    // Getters and setters
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
