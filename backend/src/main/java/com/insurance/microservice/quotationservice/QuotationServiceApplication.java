package com.insurance.microservice.quotationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuotationServiceApplication {

	public static void main(String[] args) {
		// Diagnostic Line: This will print the exact Java version being used.
		System.out.println("-----> JAVA VERSION BEING USED BY IDE: " + System.getProperty("java.version") + " <-----");

		SpringApplication.run(QuotationServiceApplication.class, args);
	}

}
