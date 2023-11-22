package fr.makizart.common.database.table;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name = "SOUNDASSET")
public class SoundAsset extends Media {

    @Column(name="PATH")
    private URI pathToRessource;

    public URI getPathToRessource() {
        return pathToRessource;
    }

    public void setPathToRessource(URI pathToRessource) {
        this.pathToRessource = pathToRessource;
    }
}
