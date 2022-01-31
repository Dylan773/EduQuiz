package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * <h1>Assets for this application</h1>
 * Stores the necessary assets for this application, whilst providing classes with easy access to the relevant assets.
 *
 * @author Dylan Brand
 */
public class Assets {

    // Game Skin
    public static final Skin SKIN = new Skin(Gdx.files.internal("skin/skin/pixthulhu-ui.json"));

    // Images/Textures
    public static final Texture
            MAIN_MENU_BACKGROUND = new Texture("images/Hidden Canyon_b.png"),
            OPTIONS_BACKGROUND = new Texture("images/background.png"),
            KNIGHT_SPRITE = new Texture("images/spellun-sprite.png");

    // Music
    public static final Music
            MAIN_MENU_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Next to You.mp3")),
            MUSEUM_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("audio/music/End credits Lofi .mp3"));

    // Sound
    public static Sound SFX_BUTTON = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/keypress-001.wav"));


    // METHODS

    /**
     * <h2>Disposes all assets in this application.</h2>S
     * Memory management method that is called to dispose all loaded resources and free up any memory
     * that inactive resources were using. Also reduces memory leak.
     * <br>
     * <p>
     * This method SHOULD only be called when the application is closed.
     */
    public static void disposeAssets() {
        // Skin
        SKIN.dispose();

        // Textures
        MAIN_MENU_BACKGROUND.dispose();
        OPTIONS_BACKGROUND.dispose();

        // Audio
        MAIN_MENU_MUSIC.dispose();
        SFX_BUTTON.dispose();
    }
}
