package com.volunteer.springbootmongo;
import com.volunteer.springbootmongo.repository.UserRepository;
import com.volunteer.springbootmongo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;


@SpringBootApplication
@EnableScheduling
public class SpringbootmongoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootmongoApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}