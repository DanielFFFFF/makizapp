package fr.makizart.database.table;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name = "SOUNDASSET")
public class SoundAsset extends Media {

    @Column(name="PATH", nullable=false)
    private URI pathToRessource;
}
