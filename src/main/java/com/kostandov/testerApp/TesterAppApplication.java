package com.kostandov.testerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class TesterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesterAppApplication.class, args);
	}

}
