package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dylan773.finalyearproject.level.QuestionData;
import xmlwise.Plist;
import xmlwise.XmlParseException;

import java.util.HashMap;

/**
 * <h1>Asset manager for this application</h1>
 * Obtains the location of all required assets, storing and providing easy access to those assets throughout this application.
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
            LOADING_SCREEN_BACKGROUND = new Texture("images/walterlicinio-ccby4.jpg"),
            KNIGHT_SPRITE = new Texture("images/spellun-sprite.png");

    // Sprites/SpriteSheets
    public static TextureAtlas spritesheet = new TextureAtlas("images/spritesheet.atlas");


    // Music
    public static final Music
            MAIN_MENU_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Next to You.mp3"));

    // Sound Effects
    public static final Sound
            SFX_BUTTON = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/keypress-001.wav")),
            CORRECT_ANS = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/correct_sound_effect.mp3")),
            INCORRECT_ANS = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/wrong_sound_effect.mp3"));

    public static QuestionData questions;

    static {
        try {
            questions = QuestionData.constructTree((HashMap<String, ?>) Plist.fromXml(Gdx.files.internal("questions/questiondata.plist").readString())); //cast to HashMap<String, ?>
        } catch (XmlParseException e) {
            e.printStackTrace();
        }
    }

    // =======
    // METHODS
    // =======

    /**
     * <h2>Disposes all assets in this application.</h2>
     * Memory management method that is called to dispose all loaded resources and free up any memory
     * that inactive resources were using. Also reduces memory leak.
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
