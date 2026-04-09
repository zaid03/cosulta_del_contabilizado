package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackendApplication.class);
    }

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
