package com.data.fzplayer.main.utils;

import android.os.Handler;

import com.google.android.exoplayer2.Player;

public class ProgressTracker implements Runnable {

        public interface PositionListener{
            public void progress(long position);
        }
    
        private final Player player;
        private final Handler handler;
        private PositionListener positionListener;
    
        public ProgressTracker(Player player, PositionListener positionListener) {
            this.player = player;
            this.positionListener = positionListener;
            handler = new Handler();
            handler.post(this);
        }
    
        public void run() {
            long position = player.getCurrentPosition();
            positionListener.progress(position);
            handler.postDelayed(this, 1000);
        }
    
        public void purgeHandler() {
            handler.removeCallbacks(this);
        }
    }