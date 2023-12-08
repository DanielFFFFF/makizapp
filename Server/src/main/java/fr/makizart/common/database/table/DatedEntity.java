package fr.makizart.common.database.table;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;



@MappedSuperclass
public abstract class DatedEntity {
    @CreationTimestamp
    @Column(name = "creationDate", updatable = false)
    protected LocalDateTime createdOn;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
