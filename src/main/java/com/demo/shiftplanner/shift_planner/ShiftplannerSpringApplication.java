package com.demo.shiftplanner.shift_planner;


//import com.demo.shiftplanner.shift_planner.config.SecurityConfig;
import com.demo.shiftplanner.shift_planner.model.Role;
import com.demo.shiftplanner.shift_planner.model.User;
import com.demo.shiftplanner.shift_planner.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class})
public class ShiftplannerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiftplannerSpringApplication.class, args);
	}
	@Bean
	public CommandLineRunner createAdminUser(UserRepository userRepository) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				String hash = new BCryptPasswordEncoder().encode("admin123");
				System.out.println("Admin user created with password admin123");
			}
		};
	}
}
