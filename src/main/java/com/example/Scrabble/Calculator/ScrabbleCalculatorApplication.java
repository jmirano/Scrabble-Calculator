package com.example.Scrabble.Calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.Scrabble.Calculator.Entities")
public class ScrabbleCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrabbleCalculatorApplication.class, args);
	}

}
