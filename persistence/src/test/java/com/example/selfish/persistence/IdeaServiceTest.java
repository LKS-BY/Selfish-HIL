package com.example.selfish.persistence;

import java.util.List;

import org.hibernate.dialect.PostgresPlusDialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.selfish.persistence.entities.Idea;
import com.example.selfish.persistence.entities.Person;
import com.example.selfish.persistence.services.IdeaService;
import com.example.selfish.persistence.services.PersonService;

import jakarta.transaction.Transactional;

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
class IdeaServiceTest {

  @Autowired IdeaService ideas;

  @Autowired PersonService people;

  @Test
  void createIdea() {
    // ARRANGE
    String title = "Test Idea";
    String description = "Test Description";

    // ACT
    Idea idea = ideas.create(title, description);

    // ASSERT
    Assertions.assertNotNull(idea.getId());
    Assertions.assertEquals(title, idea.getName());
    Assertions.assertEquals(description, idea.getDescription());
  }

  @Test
  void findById() {
    // ARRANGE
    String title = "Test Idea";
    String description = "Test Description";
    Idea idea = ideas.create(title, description);

    // ACT
    Idea foundIdea = ideas.findById(idea.getId());

    // ASSERT
    Assertions.assertNotNull(foundIdea);
    Assertions.assertEquals(idea.getId(), foundIdea.getId());
    Assertions.assertEquals(title, foundIdea.getName());
    Assertions.assertEquals(description, foundIdea.getDescription());
  }

  @Test
  void findAll() {
    // ARRANGE
    String title1 = "Test Idea 1";
    String description1 = "Test Description 1";
    String title2 = "Test Idea 2";
    String description2 = "Test Description 2";
    ideas.create(title1, description1);
    ideas.create(title2, description2);

    // ACT
    List<Idea> allIdeas = ideas.findAll();

    // ASSERT
    Assertions.assertEquals(2, allIdeas.size());
  }

  @Test
  void deleteById() {
    // ARRANGE
    String title = "Test Idea";
    String description = "Test Description";
    Idea idea = ideas.create(title, description);

    // ACT
    ideas.deleteById(idea.getId());

    // ASSERT
    Idea foundIdea = ideas.findById(idea.getId());
    Assertions.assertNull(foundIdea);
  }

  @Test
  void deleteAll() {
    // ARRANGE
    String title1 = "Test Idea 1";
    String description1 = "Test Description 1";
    String title2 = "Test Idea 2";
    String description2 = "Test Description 2";
    ideas.create(title1, description1);
    ideas.create(title2, description2);

    // ACT
    ideas.clear();

    // ASSERT
    List<Idea> allIdeas = ideas.findAll();
    Assertions.assertEquals(0, allIdeas.size());
  }
  
  @Test
  void findAllByAuthors() {
    // ARRANGE
    String title1 = "Test Idea 1";
    String description1 = "Test Description 1";
    String title2 = "Test Idea 2";
    String description2 = "Test Description 2";
    Idea idea1 = ideas.create(title1, description1);
    Idea idea2 = ideas.create(title2, description2);

    Person author1 = people.create("AuthorName1", "AuthorSurname1");
    Person author2 = people.create("AuthorName2", "AuthorSurname2");

    ideas.addAuthor(idea1.getId(), author1.getId());
    ideas.addAuthor(idea1.getId(), author2.getId());

    ideas.addAuthor(idea2.getId(), author1.getId());


    // ACT
    List<Idea> allIdeas = ideas.findAllByAuthors(author1.getId());

    // ASSERT
    Assertions.assertEquals(1, allIdeas.size());
    Assertions.assertEquals(idea1.getId(), allIdeas.get(0).getId());
    Assertions.assertEquals(title1, allIdeas.get(0).getName());

    Person author3 = people.findById(author1.getId());
    Assertions.assertNotNull(author3);
    Assertions.assertEquals(author1.getId(), author3.getId());
    Set<Idea> allIdeas2 = author3.getIdeas();
    Assertions.assertEquals(2, allIdeas2.size());
    Assertions.assertContains(idea1.getId(), allIdeas2.get(0).getId());


  }

  @Transactional
  @Test
  void symmetricContradictionIsCreated() {
    
    List<Idea> ids = ideas.findAll();
    ideas.clear();


    // ARRANGE
    Idea idea1 = ideas.create("Idea 1", "Description 1");
    Idea idea2 = ideas.create("Idea 2", "Description 2");


    // ACT
    ideas.addContradiction(idea1.getId(), idea2.getId());
    // ASSERT
    var contradiction1 = ideas.findById(idea1.getId()).getContradicts();
    var contradiction2 = ideas.findById(idea2.getId()).getContradicts();
    Assertions.assertTrue(contradiction1.contains(idea2));
    Assertions.assertTrue(contradiction2.contains(idea1));  
  }

  @Transactional
  @Test
  void contradictionIsNotCreatedIfAlreadyExists() {
      // ARRANGE
      var idea1 = ideas.create("Idea 1", "Description 1");
      var idea2 = ideas.create("Idea 2", "Description 2");

      // ACT
      ideas.addContradiction(idea1.getId(), idea2.getId());
      ideas.addContradiction(idea1.getId(), idea2.getId());

      // ASSERT
      var contradiction1 = ideas.findById(idea1.getId()).getContradicts();
      var contradiction2 = ideas.findById(idea2.getId()).getContradicts();
      Assertions.assertEquals(1, contradiction1.size());
      Assertions.assertEquals(1, contradiction2.size());
      Assertions.assertTrue(contradiction1.contains(idea2));
      Assertions.assertTrue(contradiction2.contains(idea1));
  }

}
