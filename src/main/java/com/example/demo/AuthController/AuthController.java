package com.example.demo.AuthController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Request_Response_DTO.LoginRequest;
import com.example.demo.Request_Response_DTO.LoginResponse;

@RestController
@RequestMapping("/login")
public class AuthController {

	private final AuthenticationManager authenticationManager;

	public AuthController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(req.getUsername(),
					req.getPassword());

			Authentication auth = authenticationManager.authenticate(authToken);

			
			// Extract role from authenticated user
			String role = auth.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
					.findFirst().orElse("UNKNOWN");

			// Return structured JSON
			LoginResponse response = new LoginResponse(200, "LOGIN_SUCCESS", role);
			return ResponseEntity.ok(response);

		} catch (AuthenticationException ex) {
			LoginResponse response = new LoginResponse(401, "INVALID_CREDENTIALS", null);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}
}
