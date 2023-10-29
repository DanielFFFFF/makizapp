package fr.makizart.data;

import jakarta.persistence.*;

import java.net.URL;

@Entity
@Table(name="VIDEOASSET")
public class VideoAsset {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "URL", nullable=false)
    private URL videoURL;
}
