package com.example.selfish.persistence.entities;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record IdeaDto(Long id, String name, String description,
                      List<Long> authorIds, Set<Long> contradictionIds) {

    public static IdeaDto of(Idea i) {
        return new IdeaDto(i.getId(), i.getName(), i.getDescription(),
                i.getAuthors().stream().map(Person::getId).toList(),
                i.getContradicts().stream().map(Idea::getId).collect(Collectors.toSet()));
    }
}
