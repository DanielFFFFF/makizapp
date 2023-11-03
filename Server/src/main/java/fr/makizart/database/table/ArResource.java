package fr.makizart.database.table;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name="AR_RESOURCE")
public class ArResource extends DatedEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="PATH_TO_MARKER1", nullable=false)
    private URI pathToMarker1;
    @Column(name="PATH_TO_MARKER2", nullable=false)
    private URI pathToMarker2;
    @Column(name="PATH_TO_MARKER3", nullable=false)
    private URI pathToMarker3;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageAsset thumbnail;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageAsset imageAsset;

    @OneToOne(cascade = CascadeType.ALL)
    private VideoAsset videoAsset;

    @OneToOne(cascade = CascadeType.ALL)
    private SoundAsset soundAsset;

    @PrePersist
    public void validateMutualExclusivity() {
        if ((getImageAsset() != null || getSoundAsset() != null) == (getVideoAsset() != null)) {
            throw new IllegalStateException("(ImageAsset, VideoAsset) and SoundAsset are mutually exclusive.");
        }
    }

    public Long getId() {
        return id;
    }

    public URI getPathToMarker1() {
        return pathToMarker1;
    }

    public void setPathToMarker1(URI pathToMarker1) {
        this.pathToMarker1 = pathToMarker1;
    }

    public URI getPathToMarker2() {
        return pathToMarker2;
    }

    public void setPathToMarker2(URI pathToMarker2) {
        this.pathToMarker2 = pathToMarker2;
    }

    public URI getPathToMarker3() {
        return pathToMarker3;
    }

    public void setPathToMarker3(URI pathToMarker3) {
        this.pathToMarker3 = pathToMarker3;
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
