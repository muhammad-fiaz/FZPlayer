package com.data.fzplayer.main.utils;
//https://github.com/muhammad-fiaz/FzPlayer

import java.util.HashMap;
import java.util.List;

public class MediaModel {
    private HashMap<String, List<VideoModel>> listHashMap;
    private HashMap<String,String> idlist;


    public MediaModel(HashMap<String, List<VideoModel>> listHashMap, HashMap<String,String> idlist) {
        this.listHashMap = listHashMap;
        this.idlist = idlist;
    }

    public HashMap<String, List<VideoModel>> getListHashMap() {
        return listHashMap;
    }

    public HashMap<String,String> getIdlist() {
        return idlist;
    }
}