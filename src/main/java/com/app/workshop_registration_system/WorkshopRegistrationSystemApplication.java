package com.app.workshop_registration_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WorkshopRegistrationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopRegistrationSystemApplication.class, args);
	}

}
