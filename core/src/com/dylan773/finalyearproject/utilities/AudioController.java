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

    // Default values. Can be overridden in game.
    private static float
            musicVolume = .2f,
            sfxVolume = .4f;

    private static Sound
            buttonSFX = SFX_BUTTON, // SFX Sound for button click.
            correctAnsSFX = CORRECT_ANS,
            incorrectAnsSFX = INCORRECT_ANS;

    private static Music
            nowPlaying = MAIN_MENU_MUSIC, // This applications Main Menu music.
            levelTheme;

    private static boolean isMuted = false;
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
            muteAudio(); // If mute is true, mute all audio
        else
            unmuteAudio(); // If mute is false, un-mute all audio
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
     * <h2>PLays a {@link Music} file on loop.</h2>
     * <p>
     * Sets {@link #nowPlaying} to the music file passed as a parameter. The previously {@link #nowPlaying} file is
     * disposed, while the new file's looping is set to true, from the beginning of its source.
     * <p>
     * It is worth noting, that the music file will not audibly play while {@link #isMuted} is true.
     *
     * @param music The music object to be played, from the beginning of its source.
     */
    public static void playOnLoop(Music music) {
        nowPlaying.dispose(); // Dispose the old Music file.
        nowPlaying = music; // Set nowPlaying to the provided Music file for playing.

        nowPlaying.setLooping(true); // Will continuously loop until interrupted.
        nowPlaying.setPosition(0f); // Positions the music track from the beginning.

        if (!isMuted)
            play(nowPlaying);
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
     * Sets {@link #nowPlaying} volume to the current {@link #musicVolume}.
     */
    public static void assertCorrectVolume() {
        nowPlaying.setVolume(musicVolume);
    }


    /**
     * Plays this applications Main Menu Music, only if the application is not muted.
     * <p>
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

    //================================

    public static void playSFX(Sound sound) {
        sound.play(sfxVolume);
    }

    //=============================


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
