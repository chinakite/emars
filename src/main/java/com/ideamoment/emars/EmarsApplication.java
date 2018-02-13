package com.ideamoment.emars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class EmarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmarsApplication.class, args);
	}
}
