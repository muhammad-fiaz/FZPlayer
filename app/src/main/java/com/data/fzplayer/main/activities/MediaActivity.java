package com.data.fzplayer.main.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.data.fzplayer.R;
import com.data.fzplayer.main.utils.MediaModel;
import com.data.fzplayer.main.utils.Utility;
import com.data.fzplayer.main.utils.VideoModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//https://github.com/muhammad-fiaz/FzPlayer
public class MediaActivity extends AppCompatActivity {

    private ArrayList<VideoModel> videoModels=new ArrayList<>();
    private String foldername;
    List<String> latestid=new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            foldername=bundle.getString("foldername");
            getSupportActionBar().setTitle(foldername);
//            Gson gson = new Gson();
//            Type listOfdoctorType = new TypeToken<ArrayList<VideoModel>>() {}.getType();
//           videoModels = gson.fromJson(bundle.getString("jsondata"),listOfdoctorType );

        }
        recyclerView=findViewById(R.id.mediarecyclerview);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        final int spacing = getResources().getDimensionPixelSize(R.dimen.spacing) / 2;

// apply spacing
        recyclerView.setPadding(spacing, spacing, spacing, spacing);
        recyclerView.setClipToPadding(false);
        recyclerView.setClipChildren(false);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });
        recyclerView.setAdapter(new MyMediaAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();

        MediaModel mediaModel= Utility.getAllMedia(this);
        videoModels= (ArrayList<VideoModel>) mediaModel.getListHashMap().get(foldername);
        if(videoModels==null)videoModels=new ArrayList<>();
        for (VideoModel model:videoModels) {
            Log.e("name",model.getName()+"j");
        }
        Collections.sort(videoModels, new Comparator<VideoModel>() {
            @Override
            public int compare(VideoModel t1, VideoModel t2) {
                return t1.getName().toLowerCase().compareTo(t2.getName().toLowerCase());
            }
        });

        latestid=Utility.checklatest(this,mediaModel.getListHashMap(),mediaModel.getIdlist()).get(foldername);
        Log.e("latestid",latestid+"");
        if(latestid==null)latestid=new ArrayList<>();
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyMediaAdapter extends RecyclerView.Adapter<MyMediaAdapter.ViewHolder>{

        // RecyclerView recyclerView;


        @NonNull
        @Override
        public MyMediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.utils_videoitem, parent, false);
            MyMediaAdapter.ViewHolder viewHolder = new MyMediaAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyMediaAdapter.ViewHolder holder, int position) {

            Glide.with(MediaActivity.this)
                    .load(videoModels.get(position).getUrl())
                    .transform(new CenterCrop(),new RoundedCorners(20))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail);
            holder.titletext.setText(videoModels.get(position).getName());
            holder.durationtext.setText(videoModels.get(position).getDuration());
            if(latestid.contains(videoModels.get(position).getMediaid()+"")){
                holder.newlabel.setVisibility(View.VISIBLE);
            }
            else {
                holder.newlabel.setVisibility(View.INVISIBLE);
            }
        }



        @Override
        public int getItemCount() {
            return videoModels.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView thumbnail;
            public TextView titletext;
            public TextView durationtext;
            public TextView newlabel;
            public ViewHolder(View itemView) {
                super(itemView);
                this.thumbnail = (ImageView) itemView.findViewById(R.id.imageView2);
                this.titletext=itemView.findViewById(R.id.textView);
                this.durationtext=itemView.findViewById(R.id.duration);
                this.newlabel=itemView.findViewById(R.id.newlabel);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle =   MediaActivity.this.getIntent().getExtras();
                        if(bundle!=null){

                            Gson gson=new Gson();
                            Intent intent=new Intent(MediaActivity.this, MainActivity.class);
                            intent.putExtra("jsondata",gson.toJson(videoModels));
                            intent.putExtra("pos",getAdapterPosition());
                            startActivity(intent);



                        }
                    }
                });

            }
        }
    }
}