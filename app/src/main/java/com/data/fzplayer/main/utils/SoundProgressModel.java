package com.data.fzplayer.main.utils;
/**
 * SoundProgressModel is an interface that represents a contract for classes that want to listen to sound progress changes.
 */
public interface SoundProgressModel {
    /**
     * Method to be called when the sound progress changes.
     *
     * @param progress The new progress value.
     */
    void onchange(int progress);
}
