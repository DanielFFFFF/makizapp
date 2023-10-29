package fr.makizart.data;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name="ArRessource")
public class ArRessource {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="PATH_TO_MARKERS", nullable=false)
    private URI pathToMarkers;

    @OneToOne
    private ImageAsset imageAsset;

    @OneToOne
    private VideoAsset videoAsset;

    @OneToOne
    private SoundAsset soundAsset;


}
