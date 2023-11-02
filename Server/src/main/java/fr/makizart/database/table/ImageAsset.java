package fr.makizart.database.table;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name = "IMAGEASSET")
public class ImageAsset {



    @Id
    @GeneratedValue
    private Long id;
    @Column(name="PATH", nullable=false)
    private URI pathToRessource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getPathToRessource() {
        return pathToRessource;
    }

    public void setPathToRessource(URI pathToRessource) {
        this.pathToRessource = pathToRessource;
    }


}
