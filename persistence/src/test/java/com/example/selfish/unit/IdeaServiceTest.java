package com.example.selfish.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.*;
import com.example.selfish.persistence.entities.Idea;
import com.example.selfish.persistence.services.IdeaService;

@DataJpaTest(
    showSql = true,  // don't show SQL in console
    properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop", // create schema and drop it after test
        "spring.datasource.url=jdbc:tc:mysql:8.3.0:///testdb " // use in-memory database
    }
)
@Import(IdeaService.class)   // inject service with real repositories
class IdeaServiceTest {

  @Autowired IdeaService ideas;

  @Test
  void symmetricContradictionIsCreated() {
    

    // ARRANGE
    var idea1 = ideas.create("Idea 1", "Description 1");
    var idea2 = ideas.create("Idea 2", "Description 2");


    // ACT
    ideas.addContradiction(idea1.getId(), idea2.getId());


    // ASSERT
    var contradiction1 = ideas.findById(idea1.getId()).getContradicts();
    var contradiction2 = ideas.findById(idea2.getId()).getContradicts();
    Assertions.assertTrue(contradiction1.contains(idea2));
    Assertions.assertTrue(contradiction2.contains(idea1));  
  }

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
