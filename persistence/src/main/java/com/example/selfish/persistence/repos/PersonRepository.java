package com.example.selfish.persistence.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.selfish.persistence.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

  List<Person> findByLastName(String lastName);

  Optional<Person> findById(long id);

}