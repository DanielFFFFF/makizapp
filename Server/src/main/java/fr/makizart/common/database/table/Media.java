package fr.makizart.common.database.table;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Media extends DatedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    protected String name;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
