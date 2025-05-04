package com.example.selfish.persistence.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.selfish.persistence.entities.Idea;
import com.example.selfish.persistence.entities.Person;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    // Custom query methods can be defined here if needed
    // For example:
    Optional<Idea> findByName(String name);
    Optional<Idea> findById(long id); // Find an idea by its ID
    List<Idea> findByDescriptionContaining(String keyword); // Find ideas by a keyword in the description
    List<Idea> findAllByAuthorsId(long authorId); // Find all ideas by a specific author's ID
    List<Idea> findAllByAuthors(Person author); // Find all ideas by a specific author
    List<Idea> findAllByAuthorsIn(List<Person> authors); // Find all ideas by a list of authors
    void deleteAll(); // Clear all ideas from the repository

}
