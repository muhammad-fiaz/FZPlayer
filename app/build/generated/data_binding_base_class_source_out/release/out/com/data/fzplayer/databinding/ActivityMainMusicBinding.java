// Generated by view binder compiler. Do not edit!
package com.data.fzplayer.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.data.fzplayer.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainMusicBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView musicArrowbackbtn;

  @NonNull
  public final TextView noSongsText;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView songsText;

  private ActivityMainMusicBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView musicArrowbackbtn, @NonNull TextView noSongsText,
      @NonNull RecyclerView recyclerView, @NonNull TextView songsText) {
    this.rootView = rootView;
    this.musicArrowbackbtn = musicArrowbackbtn;
    this.noSongsText = noSongsText;
    this.recyclerView = recyclerView;
    this.songsText = songsText;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainMusicBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainMusicBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main_music, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainMusicBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.music_arrowbackbtn;
      ImageView musicArrowbackbtn = ViewBindings.findChildViewById(rootView, id);
      if (musicArrowbackbtn == null) {
        break missingId;
      }

      id = R.id.no_songs_text;
      TextView noSongsText = ViewBindings.findChildViewById(rootView, id);
      if (noSongsText == null) {
        break missingId;
      }

      id = R.id.recycler_view;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.songs_text;
      TextView songsText = ViewBindings.findChildViewById(rootView, id);
      if (songsText == null) {
        break missingId;
      }

      return new ActivityMainMusicBinding((RelativeLayout) rootView, musicArrowbackbtn, noSongsText,
          recyclerView, songsText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}