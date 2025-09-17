package com.javatest.email_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EmailSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSchedulerApplication.class, args);
	}
	
}
