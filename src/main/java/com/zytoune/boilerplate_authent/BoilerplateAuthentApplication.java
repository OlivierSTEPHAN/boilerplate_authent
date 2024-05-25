package com.zytoune.boilerplate_authent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BoilerplateAuthentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoilerplateAuthentApplication.class, args);
	}

}
