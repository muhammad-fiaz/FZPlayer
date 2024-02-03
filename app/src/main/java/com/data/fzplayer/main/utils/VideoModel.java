package com.data.fzplayer.main.utils;

/**
 * VideoModel is a class that represents a video with its associated details.
 */
public class VideoModel {
    // URL of the video
    private final String url;
    // Unique identifier for the media
    private final int mediaid;
    // Name of the video
    private final String name;
    // Duration of the video
    private final String duration;

    /**
     * Constructor for the VideoModel class.
     *
     * @param url      The URL of the video.
     * @param mediaid  The unique identifier for the media.
     * @param name     The name of the video.
     * @param duration The duration of the video.
     */
    public VideoModel(String url, int mediaid, String name,String duration) {
        this.url = url;
        this.mediaid = mediaid;
        this.name = name;
        this.duration=duration;
    }

    /**
     * Gets the duration of the video.
     *
     * @return The duration of the video.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Gets the URL of the video.
     *
     * @return The URL of the video.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the unique identifier for the media.
     *
     * @return The unique identifier for the media.
     */
    public int getMediaid() {
        return mediaid;
    }

    /**
     * Gets the name of the video.
     *
     * @return The name of the video.
     */
    public String getName() {
        return name;
    }
}