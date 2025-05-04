package com.example.selfish.persistence.api;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.selfish.persistence.entities.Idea;
import com.example.selfish.persistence.services.IdeaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
public class IdeaController {

    @Autowired 
    IdeaService ideas;

    /* ---------- CRUD ---------- */
    @PostMapping
    public ResponseEntity<Idea> create(@RequestBody Idea body, UriComponentsBuilder u) {
        var saved = ideas.update(body);
        return ResponseEntity
               .created(u.path("/api/ideas/{id}").build(saved.getId()))
               .body(saved);
    }

    @GetMapping("/all")
    public List<Idea> listAll() { return ideas.findAll(); }

    @GetMapping("/{id}")
    public Idea get(@PathVariable long id) { return ideas.findById(id); }

    /* ---------- relations ---------- */
    @PostMapping("/{id}/authors/{personId}")
    public Idea addAuthor(@PathVariable long id, @PathVariable long personId) {
        return ideas.addAuthor(id, personId);
    }

    @PostMapping("/{id}/contradicts/{otherId}")
    public Set<Idea> contradicts(@PathVariable long id, @PathVariable long otherId) {
        ideas.addContradiction(id, otherId);
        return null;
    }

    @GetMapping("/{id}/contradictions")
    public Set<Idea> listContradictions(@PathVariable long id) {
        return ideas.findById(id).getContradicts();
    }
}