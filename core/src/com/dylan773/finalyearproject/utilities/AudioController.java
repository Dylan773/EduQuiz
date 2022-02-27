package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.dylan773.finalyearproject.level.GameLevel;

import static com.dylan773.finalyearproject.utilities.Assets.*;

/**
 * <h1>Audio handler for this application</h1>
 *
 * @author Dylan Brand
 */
public class AudioController {

    // ======
    // FIELDS
    // ======

    /**
     * Default volumes for music and sfx.
     */
    private static float
            musicVolume = .2f,
            sfxVolume = .4f; // Default values. Can be overridden in game.

    private static Sound buttonSFX = SFX_BUTTON; // SFX Sound for button click.
    private static Sound correctAnsSFX = CORRECT_ANS; // SFX Sound for button click.
    private static Sound incorrectAnsSFX = INCORRECT_ANS; // SFX Sound for button click.
    private static Music nowPlaying = MAIN_MENU_MUSIC; // This applications Main Menu music.
    private static boolean isMuted = false;

    public static Music levelTheme;
    public static final String THEME_KEY = "theme";


    // =======
    // METHODS
    // =======

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
     * Pauses the currently playing music, {@link #nowPlaying}.
     */
    public static void pauseMusic() {
        nowPlaying.pause();
    }


    /**
     * Resumes playing the current music, only if the application is not muted.
     */
    public static void resumeMusic() {
        if (!isMuted)
            nowPlaying.play();
    }


    /**
     * Sets the applications mute status.
     *
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
     *
     * @return {@link #isMuted}.
     */
    public static Boolean getIsMuted() {
        return isMuted;
    }


    /**
     * Accepts a Music object and plays that audio file at {@link #musicVolume}.
     *
     * @param music The music object to be played.
     * @return {@link #nowPlaying}
     */
    public static Music play(Music music) {
        //TODO - dispose of no longer required music
        pauseMusic(); // Stops the current song
        nowPlaying = music; // Swap to new music object
        assertCorrectVolume(); // Makes sure the song is played at the correct volume
        resumeMusic(); // Resumes the currently playing song

        return nowPlaying;
    }


    /**
     * Accepts a Music object and plays that audio file on loop if the application is not muted.
     *
     * @param music The music object to be played, from the beginning of the file.
     */
    public static void playOnLoop(Music music) {
        if (!isMuted) {
            music.setLooping(true); // Will continuously loop until interrupted.
            music.setPosition(0f); // Starts the music track from the beginning
            play(music);
        }
    }


    /**
     * Sets the SFX volume for this application.
     *
     * @param volume The value to set the {@link #sfxVolume} volume.
     */
    public static void setSFXVolume(float volume) {
        sfxVolume = volume;
    }


    /**
     * Returns a float value indicating the sound effects volume.
     *
     * @return {@link #sfxVolume}
     */
    public static float getSFXVolume() {
        return sfxVolume;
    }


    /**
     * Sets the Music volume for this application.
     *
     * @param volume The value to set the {@link #musicVolume} volume
     */
    public static void setMusicVolume(float volume) {
        musicVolume = volume;
        assertCorrectVolume();
    }


    /**
     * Returns a float value indicating the Music volume.
     *
     * @return {@link #musicVolume}
     */
    public static float getMusicVolume() {
        return musicVolume;
    }


    /**
     * Sets {@link #nowPlaying} volume to the current music volume.
     */
    public static void assertCorrectVolume() {
        nowPlaying.setVolume(musicVolume);
    }


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
            buttonSFX.play(sfxVolume);
    }


    /**
     * Plays a brief sound effect sound, alerting the user of a correct answer input.
     */
    public static void playCorrectAns() {
        if (!isMuted)
            correctAnsSFX.play(sfxVolume);

    }


    /**
     * Plays a brief sound effect sound, alerting the user of an incorrect answer input.
     */
    public static void playIncorrectAns() {
        if (!isMuted)
            incorrectAnsSFX.play(sfxVolume);
    }


    /**
     * Plays a {@link Music} file, whose file path matches {@link GameLevel}'s {@link #THEME_KEY}.
     *
     * @param level The level that the music file will be played throughout.
     */
    public static void playLevelTheme(GameLevel level) {
        if (levelTheme != null)
            levelTheme.dispose(); // Dispose the currently loaded music file.

        if (level.getMap().getProperties().containsKey(THEME_KEY))
            levelTheme = Gdx.audio.newMusic(Gdx.files.internal("audio/music/" + level.getMap().getProperties().get(THEME_KEY).toString()));
        else throw new Error("this map does not have a theme");

        playOnLoop(levelTheme);
    }
}
