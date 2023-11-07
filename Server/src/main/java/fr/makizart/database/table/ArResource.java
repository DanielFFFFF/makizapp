package fr.makizart.database.table;

import jakarta.persistence.*;

import java.net.URI;

@Entity
@Table(name="AR_RESOURCE")
public class ArResource extends DatedEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Marker pathToMarker1;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Marker pathToMarker2;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Marker pathToMarker3;

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
