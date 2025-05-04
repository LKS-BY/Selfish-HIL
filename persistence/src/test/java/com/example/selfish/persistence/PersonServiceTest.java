package com.example.selfish.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.selfish.persistence.entities.Person;
import com.example.selfish.persistence.services.IdeaService;
import com.example.selfish.persistence.services.PersonService;

/* @DataJpaTest(
    classes = { IdeaService.class }, // specify the class to test
    showSql = true,  // don't show SQL in console
    properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop", // create schema and drop it after test
        "spring.datasource.url=jdbc:tc:mysql:8.3.0:///testdb " // use in-memory database
    }
) */
@SpringBootTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // don't replace the database with an embedded one
class PersonServiceTest {

  @Autowired IdeaService ideas;

  @Autowired PersonService people;

    @Test
    void createPerson() {
        // ARRANGE
        String firstName = "John";
        String lastName = "Doe";

        people.create(firstName, lastName);
        // ACT
        Person person = people.findByName(firstName, lastName);

        // ASSERT
        assertNotNull(person.getId());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());

    }

}
