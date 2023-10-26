package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoJava11Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoJava11Application.class, args);
	}

	@Bean
	public CommandLineRunner myRunner(){
		return args -> System.out.println("hello world");
	}
}
