package fr.makizart.common.storageservice.dto;

public class VideoSettingsDTO {
    private int videoSize;
    private double videoOpacity;
    private boolean videoLoop;
    private String id;

    // Getters et Setters
    public int getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(int videoSize) {
        this.videoSize = videoSize;
    }

    public double getVideoOpacity() {
        return videoOpacity;
    }

    public void setVideoOpacity(double videoOpacity) {
        this.videoOpacity = videoOpacity;
    }

    public boolean isVideoLoop() {
        return videoLoop;
    }

    public void setVideoLoop(boolean videoLoop) {
        this.videoLoop = videoLoop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}