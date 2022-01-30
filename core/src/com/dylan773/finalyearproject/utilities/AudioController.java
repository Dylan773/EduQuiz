package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import static com.dylan773.finalyearproject.utilities.Assets.MAIN_MENU_MUSIC;
import static com.dylan773.finalyearproject.utilities.Assets.SFX_BUTTON;

/**
 *
 */
public class AudioController {
    /*
     * Fields
    */
    /**
     * Default volumes for music and sfx.
     */
    private static float
            musicVolume = 0.2f,
            sfxVolume = 0.2f; // Default values. Can be overridden in game.
    private static boolean isMuted = false;
    private static Music nowPlaying = MAIN_MENU_MUSIC; // This applications Main Menu music.
    private static Sound buttonSound = SFX_BUTTON; // SFX Sound for button click.

    /*
     * Methods
     */
    /**
     * Pauses the currently playing song and sets {@link #isMuted} to true.
     */
    public static void muteAudio() {
        pauseMusic();
        isMuted = true;
    }

    /**
     * Resumes playing the current song and sets {@link #isMuted} to false.
     */
    public static void unmuteAudio() {
        isMuted = false; // Un-mute audio before resume music
        resumeMusic();
    }

    /**
     * Pauses the current song, {@link #nowPlaying}.
     */
    public static void pauseMusic() { nowPlaying.pause(); }

    /**
     * Resumes playing the current song, only if the application is not muted.
     */
    public static void resumeMusic() {
        if (!isMuted)
            nowPlaying.play();
    }

    /**
     * Sets this applications mute status.
     * @param mute The boolean value to set this applications mute status.
     */
    public static void setMute(boolean mute) {
        if (mute)
            muteAudio(); // If mute is set to true, mute all audio
         else
            unmuteAudio(); // If mute is set to false, un-mute all audio

    }

    /**
     * Returns a boolean result, indicating whether this application is muted or not.
     * @return {@link #isMuted}.
     */
    public static Boolean getIsMuted() { return isMuted; }

    /**
     * Accepts a Music object and plays that audio file at {@link #musicVolume}.
     * @param music The music object to be played.
     *
     * @return {@link #nowPlaying}
     */
    public static Music play(Music music) {
        pauseMusic(); // Stops the current song
        nowPlaying = music; // Swap to new music object
        assertCorrectVolume(); // Makes sure the song is played at the correct volume
        resumeMusic(); // Resumes the currently playing song

        return nowPlaying;
    }

    public static void stopNowPlaying() { nowPlaying.stop(); }

    /**
     * Accepts a Music object and plays that audio file on loop, only if the application is not muted.
     * @param music The music object to be played.
     */
    public static void playOnLoop(Music music) {
        if (!isMuted) {
            music.setLooping(true);
            //music.play();
            play(music);
        }
    }

    /**
     * Sets the SFX volume for this application.
     * @param volume The value to set the {@link #sfxVolume} volume.
     */
    public static void setSFXVolume(float volume) { sfxVolume = volume; }

    /**
     * Returns a float value indicating the sound effects volume.
     * @return {@link #sfxVolume}
     */
    public static float getSFXVolume() { return sfxVolume; }

    /**
     * Sets the Music volume for this application.
     * @param volume The value to set the {@link #musicVolume} volume
     */
    public static void setMusicVolume(float volume) {
        musicVolume = volume;
        assertCorrectVolume();
    }

    /**
     * Returns a float value indicating the Music volume.
     * @return {@link #musicVolume}
     */
    public static float getMusicVolume() { return musicVolume; }

    /**
     * Sets {@link #nowPlaying} volume to the current music volume.
     */
    public static void assertCorrectVolume() { nowPlaying.setVolume(musicVolume); }

    /**
     * Plays this applications Main Menu Music, only if the application is not muted.
     * <br><br/>
     * Calls {@link #playOnLoop(Music)} to loop the song object.
     */
    public static void playMainMenu() {
        if (!isMuted)
        playOnLoop(MAIN_MENU_MUSIC);
    }

    /**
     * Plays a button click sound effect at the {@link #sfxVolume} only if this application is not muted.
     */
    public static void playButtonSound() {
        if (!isMuted)
            buttonSound.play(sfxVolume);
    }

    /**
     * Played when user enters a game
     */
    public static void playHistoryLevel() {
        //TODO - code (add this method in the constructor of each class)
        //nowPlaying = ...
        playOnLoop(nowPlaying);
    }
}
