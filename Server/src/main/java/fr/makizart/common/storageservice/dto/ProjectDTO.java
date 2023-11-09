package fr.makizart.common.storageservice.dto;

import fr.makizart.common.database.table.ArResource;
import fr.makizart.common.database.table.Project;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectDTO(
        String name,
        LocalDateTime dateTime,
        List<String> resources
        ) {

    public ProjectDTO(Project project) {
        this(
                project.getName(),
                project.getCreatedOn(),
                project.getArResource()
                        .stream()
                        .map(ArResource::getId)
                        .map(Object::toString)
                        .toList()
        );
    }
}
