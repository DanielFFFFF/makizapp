package fr.makizart.common.database.table;

import jakarta.persistence.*;

@Entity
@Table(name="AR_RESOURCE")
public class ArResource extends DatedEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private ARjsMarker ARjsMarker;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private ImageAsset thumbnail;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageAsset imageAsset;

    @OneToOne(cascade = CascadeType.ALL)
    private VideoAsset videoAsset;

    @OneToOne(cascade = CascadeType.ALL)
    private SoundAsset soundAsset;

    @PrePersist
    public void validate() {
        boolean atLeastOneAsset = getImageAsset() != null || getSoundAsset() != null || getVideoAsset() != null;
        boolean noVideoAndSoundOrImage = (getImageAsset() != null || getSoundAsset() != null) == (getVideoAsset() != null);
        if (noVideoAndSoundOrImage) {
            throw new IllegalStateException("(ImageAsset, SoundAsset) and videoAsset are mutually exclusive.");
        }
        if (!atLeastOneAsset){
            throw new IllegalStateException("Cannot create resource without asset");
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

    public ARjsMarker getMarkers() {
        return ARjsMarker;
    }

    public void setMarkers(ARjsMarker ARjsMarker) {
        this.ARjsMarker = ARjsMarker;
    }

    public ImageAsset getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageAsset thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
