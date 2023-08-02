package com.data.fzplayer.main.utils;

public class VideoModel {
    private  String url;
    private  int mediaid;
    private  String name;
    private String duration;



    public VideoModel(String url, int mediaid, String name,String duration) {
        this.url = url;
        this.mediaid = mediaid;
        this.name = name;
        this.duration=duration;


    }


    public String getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    public int getMediaid() {
        return mediaid;
    }

    public String getName() {
        return name;
    }
}