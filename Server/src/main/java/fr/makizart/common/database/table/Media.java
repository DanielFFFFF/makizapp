package fr.makizart.common.database.table;

import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class Media extends DatedEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME")
    protected String name;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
