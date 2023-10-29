package fr.makizart.data;

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

}
