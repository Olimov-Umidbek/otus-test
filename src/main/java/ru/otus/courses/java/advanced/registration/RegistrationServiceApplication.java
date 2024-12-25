package ru.otus.courses.java.advanced.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class RegistrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationServiceApplication.class, args);
	}

}
