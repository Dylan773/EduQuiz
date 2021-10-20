package com.dylan773.finalyearproject;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import static com.dylan773.finalyearproject.Assets.MAIN_MENU_MUSIC;
import static com.dylan773.finalyearproject.Assets.SFX_BUTTON;


public class AudioController {
    //================
    // Fields
    //================
    /**
     * Default volumes for music and sfx.
     */
    private static float
            musicVolume = 0.2f,
            sfxVolume = 0.2f;
    private static boolean isMuted = false;
    private static Music nowPlaying = MAIN_MENU_MUSIC;
    private static Sound buttonSound = SFX_BUTTON;

    //================
    // Methods
    //================
    /**
     * Stops the currently playing song and sets the isMuted to true.
     * //TODO - attach links using @ to variables
     */
    public static void muteAudio() {
        pauseMusic();
        isMuted = true;
    }

    /**
     * Un-mutes all audio, and resumes playing the current song.
     */
    public static void unmuteAudio() {
        isMuted = false; // Un-mute audio before resume music
        resumeMusic();
    }

    /**
     * Pauses the currently playing song (nowPlaying).
     */
    public static void pauseMusic() {
        nowPlaying.pause();
    }

    /**
     * Resumes the current song (nowPlaying).
     */
    public static void resumeMusic() {
        if (!isMuted)
            nowPlaying.play();
    }

    /**
     * Returns {@link #isMuted}.
     * @return Boolean result.
     */
    public static Boolean getIsMuted() {
        return isMuted;
    }

    /**
     *
     * @param music
     * @return
     */
    public static Music play(Music music) {
        pauseMusic(); // Stops the current song
        nowPlaying = music; // Swap to new music object
        assertCurrentlyPlayingVolume(); // Makes sure the song is played at the correct volume
        resumeMusic(); // Resumes the currently playing song

        return nowPlaying;
    }

    /**
     *
     * @param music
     */
    public static void playOnLoop(Music music) {
        if (!isMuted) {
            music.setLooping(true);
            //music.play();
            play(music);
        }
    }

    /**
     *
     * @param mute
     */
    public static void setMute(boolean mute) {
        if (mute) {
            muteAudio();
        } else {
            unmuteAudio();
        }
    }

    /**
     *
     * @return
     */
    public static float getMusicVolume() {
        return musicVolume;
    }

    /**
     *
     * @param volume
     */
    public static void setMusicVolume(float volume) {
        musicVolume = volume;
        assertCurrentlyPlayingVolume();
    }

    /**
     * Sets currently playing to the current volume.
     */
    public static void assertCurrentlyPlayingVolume() {
        nowPlaying.setVolume(musicVolume);
    }

    /**
     *
     * @return
     */
    public static float getSFXVolume() {
        return sfxVolume;
    }

    /**
     *
     * @param volume
     */
    public static void setSFXVolume(float volume) {
        sfxVolume = volume;
    }

    /**
     *
     */
    public static void playMainMenu() {
        playOnLoop(MAIN_MENU_MUSIC);
    }



    /**
     * Played when user enters a game
     */
    public static void playGame() {
        //TODO - code
    }

    public static void playButtonSound() {
        if (!isMuted)
            buttonSound.play(sfxVolume);
    }



}
