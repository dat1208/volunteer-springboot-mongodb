package com.volunteer.springbootmongo;
import com.volunteer.springbootmongo.models.User;
import com.volunteer.springbootmongo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.management.Query;
import java.util.List;

@SpringBootApplication
public class SpringbootmongoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootmongoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository repository){
		return  args -> {
			User user = new User("Kim","Khánh","kkneee0201hihi@gmail.com","0123123123","kimkhanh001");

			repository.findUserByEmail(user.getEmail())
					.ifPresentOrElse(u -> { System.out.println("User exist");

			},()->{ repository.insert(user);
						System.out.println("Insert Suscessful");
					});
		};
	}
}
