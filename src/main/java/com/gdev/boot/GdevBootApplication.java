package com.gdev.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GdevBootApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(GdevBootApplication.class);
		application.run(args);
	}

}
