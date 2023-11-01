package fr.makizart.database.table;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name="ArRessource")
public class ArResource {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="PATH_TO_MARKERS", nullable=false)
    private URI pathToMarkers;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageAsset thumbnail;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageAsset imageAsset;

    @OneToOne(cascade = CascadeType.ALL)
    private VideoAsset videoAsset;

    @OneToOne(cascade = CascadeType.ALL)
    private SoundAsset soundAsset;

    @PreUpdate
    private void validateMutualExclusivity() {
        if ((getImageAsset() != null || getSoundAsset() != null) == (getVideoAsset() != null)) {
            throw new IllegalStateException("(ImageAsset, VideoAsset) and SoundAsset are mutually exclusive.");
        }
    }

    public URI getPathToMarkers() {
        return pathToMarkers;
    }

    public void setPathToMarkers(URI pathToMarkers) {
        this.pathToMarkers = pathToMarkers;
    }

    public ImageAsset getImageAsset() {
        return imageAsset;
    }

    public void setImageAsset(ImageAsset imageAsset) {
        this.imageAsset = imageAsset;
    }

    public VideoAsset getVideoAsset() {
        return videoAsset;
    }

    public void setVideoAsset(VideoAsset videoAsset) {
        this.videoAsset = videoAsset;
    }

    public SoundAsset getSoundAsset() {
        return soundAsset;
    }

    public void setSoundAsset(SoundAsset soundAsset) {
        this.soundAsset = soundAsset;
    }


    public ImageAsset getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageAsset thumbnail) {
        this.thumbnail = thumbnail;
    }
}
