package fr.makizart.data;

import jakarta.persistence.*;

import java.net.URI;
import java.nio.file.Path;

@Entity
@Table(name = "SOUNDASSET")
public class SoundAsset {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="PATH", nullable=false)
    private URI pathToRessource;
}
