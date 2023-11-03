package fr.makizart.database.table;

import jakarta.persistence.*;

import java.io.File;
import java.net.URI;


@Entity
@Table(name = "IMAGEASSET")
public class ImageAsset extends Media {


    @Column(name="PATH", nullable=false)
    private URI pathToRessource;



    public URI getPathToRessource() {
        return pathToRessource;
    }

    public void setPathToRessource(URI pathToRessource) {
        this.pathToRessource = pathToRessource;
    }


}
