 package com.tutorials.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TutorialsAppApplication {

	public static void main(String[] args) {
		System.out.println("Started");
		ApplicationContext springContainer=SpringApplication.run(TutorialsAppApplication.class, args);
	}

}
  