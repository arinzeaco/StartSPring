package com.example.StartSPring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.StartSPring.repository")
@EntityScan("com.example.StartSPring.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")

public class StartSPringApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartSPringApplication.class, args);
	}

}
