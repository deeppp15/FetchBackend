package com.example.fetch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class FetchApplication {

	protected static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(FetchApplication.class, args);
		logger.debug("SHIT", "Bitch");;
	}

}
