package com.data.fzplayer.main.utils;
//https://github.com/muhammad-fiaz/FzPlayer

import java.util.HashMap;
import java.util.List;
/**
 * MediaModel is a class that represents a media model with a list of video models and an ID list.
 */
public class MediaModel {
    private HashMap<String, List<VideoModel>> listHashMap;
    private HashMap<String,String> idlist;


    /**
     * Constructor for the MediaModel class.
     *
     * @param listHashMap The list of video models.
     * @param idlist The ID list.
     */
    public MediaModel(HashMap<String, List<VideoModel>> listHashMap, HashMap<String,String> idlist) {
        this.listHashMap = listHashMap;
        this.idlist = idlist;
    }
    /**
     * Gets the list of video models.
     *
     * @return The list of video models.
     */
    public HashMap<String, List<VideoModel>> getListHashMap() {
        return listHashMap;
    }

    /**
     * Gets the ID list.
     *
     * @return The ID list.
     */
    public HashMap<String,String> getIdlist() {

        return idlist;
    }
}