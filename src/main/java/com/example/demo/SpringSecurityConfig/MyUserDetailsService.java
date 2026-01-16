package com.example.demo.SpringSecurityConfig;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Users;
import com.example.demo.Repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepository userRepo;

	public MyUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("HACKER ATTEMPT - Username received from React: " + username);
		
		Users user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		System.out.println("User is loaded from database:"+user.getUsername());
		
		
		
		// here User class comming from
//        import org.springframework.security.core.userdetails.User;

		return User.withUsername(user.getUsername()).password(user.getPassword()) // DB se encoded password
				.roles(user.getRole()) // DB se role
				.build();

	}
}
