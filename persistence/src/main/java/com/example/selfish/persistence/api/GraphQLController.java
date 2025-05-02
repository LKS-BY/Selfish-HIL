package com.example.selfish.persistence.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.selfish.persistence.entities.Idea;
import com.example.selfish.persistence.entities.Person;
import com.example.selfish.persistence.services.IdeaService;
import com.example.selfish.persistence.services.PersonService;

import lombok.RequiredArgsConstructor;

// @Controller
//@RequiredArgsConstructor
public class GraphQLController {

    @Autowired 
    private IdeaService ideaService;
    @Autowired
    private PersonService personService;


    /* ---------- Queries ---------- */
    @QueryMapping
    public String idea(@Argument Long id){ return ideaService.findById(id).toString(); }

    @QueryMapping
    public List<Idea> ideas() { return ideaService.findAll(); }

    @SchemaMapping
    public List<Idea> ideas(@Argument Person person) {
        return ideaService.findAllByAuthors(person.getId());
    }

    /* ---------- Mutations -------- */

/*     @MutationMapping
    public Idea createIdea(@Argument CreateIdeaInput input)             { return ideaService.create(input); }

    @MutationMapping
    public Idea updateIdea(@Argument UpdateIdeaInput input)             { return ideaService.update(input); }

    @MutationMapping
    public Boolean deleteIdea(@Argument Long id)                        { ideaRepo.deleteById(id); return true; }

    @MutationMapping
    public Idea addAuthorToIdea(@Argument Long ideaId,
                                @Argument Long personId)                { return ideaService.addAuthor(ideaId, personId); }

    @MutationMapping
    public Idea addContradiction(@Argument Long ideaId,
                                 @Argument Long contradictingIdeaId)    { return ideaService.addContradiction(ideaId, contradictingIdeaId); }

    @MutationMapping
    public Idea removeContradiction(@Argument Long ideaId,
                                    @Argument Long contradictingIdeaId) { return ideaService.removeContradiction(ideaId, contradictingIdeaId); } */
}

