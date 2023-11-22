package fr.makizart.common.database.table;

import jakarta.persistence.*;

import java.net.URI;


@Entity
@Table(name = "IMAGEASSET")
public class ImageAsset extends Media {


    @Column(name="PATH")
    private URI pathToRessource;


    public ImageAsset(URI pathToRessource) {
        this.pathToRessource = pathToRessource;
    }

    public ImageAsset() {

    }

    public URI getPathToRessource() {
        return pathToRessource;
    }

    public void setPathToRessource(URI pathToRessource) {
        this.pathToRessource = pathToRessource;
    }


}
