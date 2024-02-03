package com.data.fzplayer.main.utils;

import android.os.Handler;

import com.google.android.exoplayer2.Player;

/**
 * ProgressTracker is a class that tracks the progress of a media player.
 */
public class ProgressTracker implements Runnable {

    /**
     * PositionListener is an interface that represents a contract for classes that want to listen to position changes.
     */
        public interface PositionListener{
        /**
         * Method to be called when the position changes.
         *
         * @param position The new position value.
         */
            public void progress(long position);
        }
    
        private final Player player;
        private final Handler handler;
        private PositionListener positionListener;
    /**
     * Constructor for the ProgressTracker class.
     *
     * @param player The media player.
     * @param positionListener The position listener.
     */
        public ProgressTracker(Player player, PositionListener positionListener) {
            this.player = player;
            this.positionListener = positionListener;
            handler = new Handler();
            handler.post(this);
        }
    /**
     * Method to be called when the runnable is going to be executed.
     * It updates the position and posts a delayed message to the handler.
     */
        public void run() {
            long position = player.getCurrentPosition();
            positionListener.progress(position);
            handler.postDelayed(this, 1000);
        }

    /**
     * Removes all callbacks and messages from the handler.
     */
        public void purgeHandler() {
            handler.removeCallbacks(this);
        }
    }