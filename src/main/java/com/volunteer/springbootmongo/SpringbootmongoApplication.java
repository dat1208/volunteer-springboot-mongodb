package com.volunteer.springbootmongo;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.repository.UserRepository;
import com.volunteer.springbootmongo.service.HDBank.AuthenticationHelper;
import com.volunteer.springbootmongo.service.firebase.upoad.UploadService;
import com.volunteer.springbootmongo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
public class SpringbootmongoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootmongoApplication.class, args);
	}
	@Autowired
	UserService userService;
	AuthenticationHelper authenticationHelper;
	@Bean
	CommandLineRunner runner(UserRepository repository){
		return  args -> {
		};
	}
}