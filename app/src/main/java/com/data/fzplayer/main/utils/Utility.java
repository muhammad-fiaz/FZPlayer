package com.data.fzplayer.main.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Utility {
    public static MediaModel getAllMedia(Context context) {
        HashMap<String, List<VideoModel>> listHashMap=new HashMap<>();
        HashMap<String,String> idhash=new HashMap<>();

        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media._ID,MediaStore.Video.Media.DURATION};
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do{
                if(cursor.getString(1)==null)continue;
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                int id = cursor.getInt(columnIndex);
                //Bitmap bitmap= MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(),id,MediaStore.Video.Thumbnails.MICRO_KIND,null);
                String path= cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                Log.e(path,cursor.getString(1)+"j");
                idhash.put(id+"","1");


                String[] divide=path.split("/");
                String foldername=divide[divide.length-2];


                VideoModel videoModel=new VideoModel(path,id,cursor.getString(1),milltominute(cursor.getInt(3)));
                if(listHashMap.containsKey(foldername)){
                    listHashMap.get(foldername).add(videoModel);
                }
                else {
                    ArrayList<VideoModel> modelArrayList=new ArrayList<>();
                    modelArrayList.add(videoModel);
                    listHashMap.put(foldername,modelArrayList);
                }

            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MediaModel(listHashMap,idhash);
    }
    public static String milltominute(long milliseconds) {
        // milliseconds to minutes.
        boolean v = false;
        if (milliseconds < 0) {
            v = true;
            milliseconds = Math.abs(milliseconds);
        }
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        String time = "";
        if (hours == 0) {
            time = String.format("%02d:%02d", minutes, seconds);
        } else {
            time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        if (v) return "-" + time;
        else return time;


    }
    public static  HashMap<String,List<String>> checklatest(Context context, HashMap<String, List<VideoModel>> videodata,HashMap<String,String> idhash){
        String[] keyset= (String[]) videodata.keySet().toArray(new String[videodata.size()]);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        HashMap<String,List<String>> latestvideo=new HashMap<>();
        latestvideo.clear();


        Gson gson = new Gson();
        if(!sharedPreferences.contains("storedata")){

            myEdit.putString("storedata",gson.toJson(idhash));
            myEdit.commit();
        }
        Type listOfdoctorType = new TypeToken<HashMap<String, String>>() {}.getType();
        HashMap<String, String>   lastids = gson.fromJson(sharedPreferences.getString("storedata",""),listOfdoctorType );
        if(lastids==null)lastids=new HashMap<>();

        for(String key:keyset){

            for(VideoModel videoModel:videodata.get(key)){
                if(!lastids.containsKey(videoModel.getMediaid()+"")){
                    if(latestvideo.containsKey(key)){
                        List<String> stringList=latestvideo.get(key);
                        stringList.add(videoModel.getMediaid()+"");
                        latestvideo.put(key,stringList);
                    }
                    else {
                        List<String> newlist=new ArrayList<>();
                        newlist.add(videoModel.getMediaid()+"");
                        latestvideo.put(key,newlist);
                    }
                }
            }
        }
        return latestvideo;
    }
}