package com.example.selfish.persistence;

import org.hibernate.jpa.boot.spi.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.selfish.persistence.entities.Person;
import com.example.selfish.persistence.repos.PersonRepository;
import com.example.selfish.persistence.services.PersonService;

@SpringBootApplication
public class PersistenceApplication {

	private static final Logger log = LoggerFactory.getLogger(PersistenceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PersistenceApplication.class, args);
	}

}
