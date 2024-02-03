package com.data.fzplayer.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

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

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * MainFolderActivity is an activity class that represents the main folder screen of the application.
 */
public class MainFolderActivity extends AppCompatActivity
{
    private static final int REQUEST_VIDEO_PERMISSION = 200;
    private MyListAdapter myListAdapter;
    private HashMap<String, List<VideoModel>> videodata;
    private HashMap<String, List<String>> latestvideo = new HashMap<>();

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped, but is now again being displayed to the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    /**
     * Checks if the necessary permissions are granted.
     */
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

    /**
     * Shows a popup menu when the menu icon is clicked.
     *
     * @param view The view that was clicked.
     */
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_main);

        // Set item click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle menu item clicks here
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Intent intent = new Intent(MainFolderActivity.this, SettingsActivity.class); // Replace SettingsActivity with your actual settings activity class
                        startActivity(intent);
                        return true;
                    // Add more cases for other menu items if needed
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    /**
     * Gets the media data.
     */
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

    /**
     * Called when the user responds to the permission request.
     *
     * @param requestCode The request code passed in requestPermissions(android.app.Activity, String[], int).
     * @param permissions The requested permissions.
     * @param grantResults The grant results for the corresponding permissions which is either PERMISSION_GRANTED or PERMISSION_DENIED.
     */
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

    /**
     * Gets the exif orientation of the media file.
     *
     * @param filepath The path of the media file.
     * @return The orientation of the media file.
     */
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

    /**
     * MyListAdapter is an inner class that represents the adapter for the RecyclerView.
     */
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

        /**
         * ViewHolder is an inner class that represents the view holder for the RecyclerView.
         */
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