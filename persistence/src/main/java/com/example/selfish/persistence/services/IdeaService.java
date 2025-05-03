package com.example.selfish.persistence.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.selfish.persistence.entities.Idea;
import com.example.selfish.persistence.entities.Person;
import com.example.selfish.persistence.repos.IdeaRepository;
import com.example.selfish.persistence.repos.PersonRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class IdeaService  {

    @Autowired 
    private IdeaRepository ideas;
    @Autowired
    private PersonRepository people;

    public List<Idea> findAll() {
        return ideas.findAll();
    }

    public List<Idea> findAllByAuthors(long authorId) {
        return ideas.findAllByAuthorsId(authorId);
    }

    public void clear() {
        ideas.deleteAll();
    }

/*     @Transactional
    public Idea addIdea(IdeaDto dto, List<Long> authorIds) {
        Idea idea = mapper.toEntity(dto);
        List<Person> authors = personRepo.findAllById(authorIds);
        idea.getAuthors().addAll(authors);
        return ideaRepo.save(idea);
    }

    @Transactional(readOnly = true)
    public Page<Idea> searchIdeas(String keyword, Pageable page) {
        return ideaRepo.findAll(
            (root, q, cb) -> cb.like(cb.lower(root.get("title")),
                                     "%" + keyword.toLowerCase() + "%"), page);
    } */
    public Idea create(String name, String description) {
        Idea idea = new Idea(name, description);
        return ideas.save(idea);
    }
    @Transactional
    public Idea create(String name, String description, Set<Person> authors) {
        Idea idea = create(name, description);
        idea.getAuthors().addAll(authors);
        return ideas.save(idea);
    }
    @Transactional
    public Idea update(Idea idea) {
        return ideas.save(idea);
    }

    public Idea findById(long id) {
        Optional<Idea> result = ideas.findById(id);
        if (result.isPresent())
            return result.get();
        return null;
    }

    public void deleteById(long id) {
        ideas.deleteById(id);
    }


    /* declare that two ideas contradict each other */
    public void addContradiction(long aId, long bId) {
        if (aId == bId) throw new IllegalArgumentException("An idea cannot contradict itself");
        var a = ideas.findById(aId).orElseThrow();
        var b = ideas.findById(bId).orElseThrow();
        addContradiction(a, b); // A -> B
    }

    public void addContradiction(Idea idea, Idea contradiction) {
        Set<Idea> contradictions = idea.getContradicts();
        Set<Idea> contradictionsInv = contradiction.getContradicts();
        if (contradictions.contains(contradiction)  // A -> B already exists
            || contradictionsInv.contains(idea)) // B -> A already exists
            return; // do nothing
        contradictions.add(contradiction);
        contradictionsInv.add(idea);
    }

    public void removeContradiction(long aId, long bId) {
        var a = ideas.findById(aId).orElseThrow();
        var b = ideas.findById(bId).orElseThrow();
        a.getContradicts().remove(b); // A -/-> B
        b.getContradicts().remove(a); // B -/-> A (symmetry)
    }

    public Set<Idea> listContradictions(long ideaId) {
        return findById(ideaId).getContradicts();
    }

    public Idea addAuthor(long ideaId, long personId) {
        var idea   = ideas.findById(ideaId)
                        .orElseThrow(() -> new EntityNotFoundException("idea"));
        var person = people.findById(personId)
                        .orElseThrow(() -> new EntityNotFoundException("person"));
        idea.getAuthors().add(person);
        person.getIdeas().add(idea);
        return idea;
    }

    public void addAuthor(Idea idea, Person author) {
        idea.getAuthors().add(author);
    }

    public void addAuthors(Idea idea, Set<Person> authors) {
        idea.getAuthors().addAll(authors);
    }

    public void removeAuthor(Idea idea, Person author) {
        idea.getAuthors().remove(author);
    }

    /* remove all authors from the idea */
  public void removeAuthors(Idea idea, Set<Person> authors) {
      idea.getAuthors().removeAll(authors);
    }

    public List<Idea> findByDescriptionContaining(String keyword) {
        return ideas.findByDescriptionContaining(keyword);
    }

    public List<Idea> findByName(String name) {
        return ideas.findByName(name);
    }

    public List<Idea> findAllByAuthorsIn(List<Person> authors) {
        return ideas.findAllByAuthorsIn(authors);
    }


}
