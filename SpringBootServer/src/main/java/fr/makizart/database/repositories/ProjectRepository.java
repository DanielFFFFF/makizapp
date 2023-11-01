package fr.makizart.database.repositories;

import fr.makizart.database.table.ImageAsset;
import fr.makizart.database.table.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}

