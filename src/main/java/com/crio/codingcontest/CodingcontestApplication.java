package com.crio.codingcontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.crio.codingcontest.repositories")
public class CodingcontestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingcontestApplication.class, args);
	}

}
