package fr.makizart.common.database.table;

import jakarta.persistence.*;

import java.net.URL;

@Entity
@Table(name="VIDEOASSET")
public class VideoAsset extends Media {

    @Column(name = "URL", nullable=false)
    private URL videoURL;

    public URL getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(URL videoURL) {
        this.videoURL = videoURL;
    }
}
