package fr.makizart.database.table;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name = "SOUNDASSET")
public class SoundAsset {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="PATH", nullable=false)
    private URI pathToRessource;
}
