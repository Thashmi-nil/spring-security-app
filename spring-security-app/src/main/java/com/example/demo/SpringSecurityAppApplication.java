package com.example.demo;

import com.example.demo.entities.Authority;
import com.example.demo.entities.User;
import com.example.demo.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringSecurityAppApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppApplication.class, args);
		System.out.println("can login");
	}

	@PostConstruct
	protected void init(){
		List <Authority> authorityList=new ArrayList<>();

		authorityList.add(createAuthority("USER", "user_role"));
		authorityList.add(createAuthority("ADMIN", "admin_role"));

		User user = new User();

		user.setUserName("sanjana123");
		user.setFirstName("Sanjana");
		user.setLastName("Raj");

		user.setPassword(passwordEncoder.encode("san@123"));
		user.setEnabled(true);
		user.setAuthorities(authorityList);

		userDetailsRepository.save(user);
	}

	private Authority createAuthority(String roleCode,String roleDescription) {
		Authority authority=new Authority();
		authority.setRoleCode(roleCode);
		authority.setRoleDescription(roleDescription);
		return authority;
	}

}
