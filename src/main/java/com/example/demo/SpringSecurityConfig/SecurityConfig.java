package com.example.demo.SpringSecurityConfig;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.Security.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final MyUserDetailsService userDetailsService;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	// in this class only constractor so this is automatically invoked by ioc
	// after spring boot 2 version so no need to write @Aut

	public SecurityConfig(MyUserDetailsService userDetailsService, CustomAccessDeniedHandler accessDeniedHandler,
			CustomAuthenticationEntryPoint authenticationEntryPoint) {

		this.userDetailsService = userDetailsService;
		this.accessDeniedHandler = accessDeniedHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	// Password encryption
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// DB se user validate karega this is used and support in only spring boot 2
	// and spring boot security 5
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
//
//		provider.setPasswordEncoder(passwordEncoder());
//		return provider;
//	}

	// this is new we can used with 3.2.5 with spring security 6

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())

				// 1. Frame Options disable karein (H2 Console ke liye zaroori hai)
				.headers(headers -> headers.frameOptions(frame -> frame.disable()))
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						.requestMatchers("/h2-console/**").permitAll()
						// public endpoints
						.requestMatchers("/login").permitAll()

						.requestMatchers("/student/getAllStudent").hasAnyRole("USER", "ADMIN")

						.requestMatchers("/student/addStudent").hasRole("ADMIN")

						.requestMatchers("/student/updateStudent/**").hasRole("ADMIN")

						.requestMatchers("/student/deleteStudent/**").hasRole("ADMIN").anyRequest().authenticated())

				// form login band
				.formLogin(form -> form.disable())

				// BASIC AUTH enable
				.httpBasic(Customizer.withDefaults())

				// JSON errors if user credential not vallide then trigger
				// .authenticationEntryPoint
				// else 403 role not match then 403

				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))

				.logout(logout -> logout.permitAll());

		return http.build();
	}

}
