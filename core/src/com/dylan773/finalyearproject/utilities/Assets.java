package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Dylan Brand
 */
public class Assets {

    // Game Skin
    public static final Skin SKIN = new Skin(Gdx.files.internal("skin/skin/pixthulhu-ui.json"));

    // Main Menu Background Image
    public static final Texture MENU_BACKGROUND = new Texture("images/Hidden Canyon_b.png");

    public static final Music
    MAIN_MENU_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Next to You.mp3"));
    //MAIN_MENU_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Cleyton RX - Underwater.mp3"));

    public static Sound SFX_BUTTON = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/keypress-001.wav"));


    /**
     * <h2>Disposes all assets in this application.</h2>
     * Memory management method that is called to dispose all loaded resources and free up any memory
     * that inactive resources were using. Also preventing memory leak.
     */
    public static void disposeAssets() {
        SKIN.dispose();
        MENU_BACKGROUND.dispose();
        MAIN_MENU_MUSIC.dispose();
        SFX_BUTTON.dispose();

    }
}
