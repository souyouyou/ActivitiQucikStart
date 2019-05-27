package com.krxinuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.krxinuo","org.activiti"})
public class ActivitiQucikStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiQucikStartApplication.class, args);
	}
}
