package com.scheduly.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SchedulyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulyApiApplication.class, args);
	}

}
