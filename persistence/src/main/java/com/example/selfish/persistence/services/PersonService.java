package com.example.selfish.persistence.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.selfish.persistence.PersistenceApplication;
import com.example.selfish.persistence.entities.Person;
import com.example.selfish.persistence.repos.PersonRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersistenceApplication.class);

 
    @Autowired 
    private PersonRepository personRepository;
 

    

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new EntityNotFoundException("Person not found with id: " + id);
        }
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
    public Person update(Person person) {
        personRepository.save(person);
        log.info("Updated person: " + person.toString());
        return person;
    }

    public Person create(String firstName, String lastName) {
        // Check if a person with the same first and last name already exists
        List<Person> existingPersons = personRepository.findByLastName(lastName);
        for (Person existingPerson : existingPersons) {
            if (existingPerson.getFirstName().equalsIgnoreCase(firstName)) {
                log.info("Person with the same name already exists: " + existingPerson.toString());
                return existingPerson; // Return the existing person
            }
        }
        Person person = new Person(firstName, lastName);
        // Save the person to the repository (assuming you have a repository instance)
        personRepository.save(person);
        log.info("Created person: " + person.toString());
        return person;
    }

    public Person create( String firstNameSemicolonLastName) {
        String[] names = firstNameSemicolonLastName.split(";");
        if (names.length != 2) {
            throw new IllegalArgumentException("Input must be in the format 'FirstName;LastName'");
        }
        return create(names[0], names[1]);

    }
}
