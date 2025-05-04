package com.example.selfish.persistence.entities;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ideas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 4_000)
    private String description;

    /* authors */
    @JoinTable(name = "idea_authors",
        joinColumns = @JoinColumn(name = "idea_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id"))
    @ManyToMany
    @JsonManagedReference
    private Set<Person> authors = new HashSet<>();

    public Idea(String name, String description ) {
        this.name = name;
        this.description = description;
    }

        /* --- contradictions -------------------------------------------------- */
        @ManyToMany
        @JsonBackReference
        @JoinTable(name = "idea_contradictions",
                   joinColumns        = @JoinColumn(name = "idea_id"),
                   inverseJoinColumns = @JoinColumn(name = "contradicts_id"))
        private Set<Idea> contradicts = new HashSet<>();
/*     
        @ManyToMany(mappedBy = "contradicts")  // inverse view
        private Set<Idea> contradictedBy = new HashSet<>();

 */
    @Override
    public String toString() {
        return String.format(
            "Idea[id=%d, name='%s', description='%s']",
            id, name, description);
    }



}
