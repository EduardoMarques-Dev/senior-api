package com.emarques.seniorapi;

import com.emarques.seniorapi.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
//@ComponentScan({"com.spring","com.emarques.seniorapi.api.util.ModelConverter"})
public class SeniorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeniorApiApplication.class, args);
	}

}
