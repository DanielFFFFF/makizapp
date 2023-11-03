package fr.makizart.database.table;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Media extends DatedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "SIZE")
    private long sizeOnServerDisk;


    public long getSizeOnServerDisk() {
        return sizeOnServerDisk;
    }

    public void setSizeOnServerDisk(long sizeOnServerDisk) {
        this.sizeOnServerDisk = sizeOnServerDisk;
    }


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
