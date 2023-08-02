package com.data.fzplayer.main.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.data.fzplayer.R;
import com.data.fzplayer.main.utils.MediaModel;
import com.data.fzplayer.main.utils.Utility;
import com.data.fzplayer.main.utils.VideoModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainFolderActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_VIDEO_PERMISSION = 200;
    private MyListAdapter myListAdapter;
    private HashMap<String, List<VideoModel>> videodata;
    private HashMap<String, List<String>> latestvideo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        RecyclerView folderrecycleview = findViewById(R.id.folderrecyclerview);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        folderrecycleview.setLayoutManager(layoutManager);

        myListAdapter = new MyListAdapter(null);
        folderrecycleview.setAdapter(myListAdapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null); // Set to null to show white icons
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.action_local); // Set "Local" as default selected menu item

    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_local:
                // No need to do anything as we are already in FolderActivity (Local)
                break;
            case R.id.action_musics:
                // Show "Musics" page
                break;
            case R.id.action_settings:
                // Show "Settings" page
                // Handle the logic to show the "Settings" page here
                Intent musicIntent = new Intent(MainFolderActivity.this, SettingsActivity.class);
                startActivity(musicIntent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private void checkPermission() {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request storage permission for video files
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_VIDEO_PERMISSION);
        } else {
            // Permission already granted, proceed with getting media data
            getMediaData();
        }
    }

    private void getMediaData() {
        MediaModel mediaModel = Utility.getAllMedia(this);
        videodata = mediaModel.getListHashMap();
        String[] keyset = videodata.keySet().toArray(new String[videodata.size()]);

        latestvideo = Utility.checklatest(this, videodata, mediaModel.getIdlist());
        Arrays.sort(keyset, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        });
        Log.e("latestinfo", Arrays.toString(keyset));

        myListAdapter.setListdata(keyset);
        myListAdapter.notifyDataSetChanged();
    }

    // This function is called when user accepts or declines the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompted for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_VIDEO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with getting media data
                getMediaData();
            } else {
                // Permission denied
                // Handle the case where permission is not granted by showing an error message
            }
        }
    }

    // This method gets the exif orientation of the media file
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
        private String[] listdata;

        public MyListAdapter(String[] listdata) {
            this.listdata = listdata;
        }

        public void setListdata(String[] listdata) {
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.utils_folderitem, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String foldername = listdata[position];
            String capatilize = foldername.substring(0, 1).toUpperCase() + foldername.substring(1).toLowerCase();
            holder.foldernameview.setText(capatilize);
            holder.noofvideoview.setText(videodata.get(listdata[position]).size() + " videos");
            if (latestvideo.containsKey(listdata[position])) {
                holder.badgeview.setText(latestvideo.get(listdata[position]).size() + "");
                holder.badgeview.setVisibility(View.VISIBLE);
            } else {
                holder.badgeview.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return listdata == null ? 0 : listdata.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView foldernameview;
            public TextView noofvideoview;
            public TextView badgeview;

            public ViewHolder(View itemView) {
                super(itemView);
                this.foldernameview = itemView.findViewById(R.id.foldername);
                this.noofvideoview = itemView.findViewById(R.id.noofvideo);
                this.badgeview = itemView.findViewById(R.id.badgeview);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainFolderActivity.this, MediaActivity.class);
                        intent.putExtra("foldername", listdata[getAdapterPosition()]);
                        Gson gson = new Gson();
                        String json = gson.toJson(videodata.get(listdata[getAdapterPosition()]));
                        intent.putExtra("jsondata", json);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
