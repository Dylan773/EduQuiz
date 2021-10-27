package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.AudioController;
import static com.dylan773.finalyearproject.utilities.Utilities.addLabel;

/**
 * <h1>Game Options Window</h1>
 * This window is responsible for the handling of this application's settings.
 *
 * @author Dylan Brand
 */
public class OptionsWindow extends Window {

    /**
     * <h2>Options Window Constructor</h2>
     * Constructs the Options window to be displayed.<br>
     * The {@link #setVisible(boolean)} method can be called to set the visibility of this window to the user.
     */
    public OptionsWindow() {
        super("", Assets.SKIN);

        this.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.OPTIONS_BACKGROUND)));
        this.setResizable(false);
        this.setMovable(false);
        this.setVisible(false); // By default, this window is initially hidden
        this.setSize(1000f, 600f);
        this.setPosition(150, 50); // TODO - I dont like this hard coded, center using a different method
        this.top();

        // TODO - why do these all have to be final???
        // Game Music Control
        final Slider musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
        musicSlider.setValue(AudioController.getMusicVolume());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioController.setMusicVolume(musicSlider.getValue());
                AudioController.playButtonSound();
            }
        });

        // Game Sound Effect's Control
        final Slider sfxSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
        sfxSlider.setValue(AudioController.getSFXVolume());
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioController.setSFXVolume(sfxSlider.getValue());
                AudioController.playButtonSound();
            }
        });

        // Master Audio Mute Control
        final CheckBox muteCheck = new CheckBox("Mute", Assets.SKIN);
        muteCheck.setChecked(AudioController.getIsMuted());
        muteCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioController.setMute(muteCheck.isChecked());
            }
        });

        // Add each actor to the window
        this.add(addLabel("OPTIONS", "title")).padTop(40f).colspan(2).row();
        this.add(addLabel("Audio", "subtitle")).padBottom(10f).colspan(2).row();
        this.add(addLabel("Music Volume:", "default")).right().padBottom(5f);
        this.add(musicSlider).fillX().padBottom(5f).row();
        this.add(addLabel("SFX Volume:", "default")).right().padBottom(5f);
        this.add(sfxSlider).fillX().padBottom(5f).row();
        this.add(muteCheck).padBottom(130f).colspan(2).row();

        // Label exception, has orange text
        Label menuExit = new Label("Press ESC to Exit", Assets.SKIN, "font", Color.ORANGE);
        this.add(menuExit).colspan(2);
    }
}

