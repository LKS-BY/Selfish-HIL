package com.example.selfish.persistence.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.selfish.persistence.services.PersonService;
import com.example.selfish.persistence.services.IdeaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.selfish.persistence.entities.Person;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private PersonService people;

    @PostMapping
    public Person create(@RequestBody Person p) { return people.update(p); } // TODO: check if exists
}
