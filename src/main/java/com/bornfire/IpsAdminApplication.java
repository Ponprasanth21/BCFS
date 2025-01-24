package com.bornfire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IpsAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpsAdminApplication.class, args);
	}

}
