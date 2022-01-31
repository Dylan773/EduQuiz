package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.AudioController;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.*;
import static com.dylan773.finalyearproject.utilities.Utilities.addLabel;

/**
 * <h1>This applications Options Window</h1>
 * This window is responsible for the handling of this application's settings.
 *
 * @author Dylan Brand
 */
public class OptionsWindow extends WindowBuilder {

    /**
     * <h2>Options Window Constructor</h2>
     * Constructs the Options window to be displayed.
     * <p>
     * The {@link #setVisible(boolean)} method can be called to set the visibility of this window to the user.
     */
    public OptionsWindow() {
        super(1000f, 600f);
        buildWindow();

    }

    /**
     * Overrides the abstract buildWindow() method from the {@link #WindowBuilder}  parent class.
     */
    @Override
    protected void buildWindow() {
        setVisible(false); // Not shown by default.

        // Game Music Control
        final Slider musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, SKIN);
        musicSlider.setValue(AudioController.getMusicVolume());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioController.setMusicVolume(musicSlider.getValue());
                playButtonSound();
            }
        });

        // Game Sound Effect's Control
        final Slider sfxSlider = new Slider(0.0f, 1.0f, 0.1f, false, SKIN);
        sfxSlider.setValue(AudioController.getSFXVolume());
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioController.setSFXVolume(sfxSlider.getValue());
                playButtonSound();
            }
        });

        // Master Audio Mute Control
        final CheckBox muteCheck = new CheckBox("Mute", SKIN);
        muteCheck.setChecked(AudioController.getIsMuted());
        muteCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioController.setMute(muteCheck.isChecked());
            }
        });

        TextButton btnClose = new TextButton("Close", SKIN);
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                setVisible(false);
            }
        });

        // Add each actor to the window
        add(addLabel("OPTIONS", "title")).colspan(2).row();
        add(addLabel("Audio", "subtitle")).padBottom(10f).colspan(2).row();
        add(addLabel("Music Volume:", "default")).right().padBottom(5f);
        add(musicSlider).fillX().padBottom(5f).row();
        add(addLabel("SFX Volume:", "default")).right().padBottom(5f);
        add(sfxSlider).fillX().padBottom(5f).row();
        add(muteCheck).colspan(2).padBottom(70f).row();

        add(btnClose).colspan(2);

        // Label exception, has orange text
//        Label menuExit = new Label("Press ESC to Exit", SKIN, "font", Color.ORANGE);
//        this.add(menuExit).colspan(2);
    }
}
