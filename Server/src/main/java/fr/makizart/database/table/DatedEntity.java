package fr.makizart.database.table;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;



@MappedSuperclass
public abstract class DatedEntity {
    @CreationTimestamp
    @Column(name = "creationDate")
    private LocalDateTime createdOn;
}
