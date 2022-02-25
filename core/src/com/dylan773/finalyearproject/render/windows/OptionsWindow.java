package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.utilities.AudioController;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.*;
import static com.dylan773.finalyearproject.utilities.Utilities.destroyActor;

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
        initWindow();
    }


    /**
     * Overrides the abstract initWindow method from the {@link #WindowBuilder} parent class.
     */
    @Override
    protected void initWindow() {
        setVisible(true); // Not shown by default. //TODO - change

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
                destroyActor(OptionsWindow.this);
            }
        });

        // Add each actor to the window
        addLabel("OPTIONS", "title").colspan(2).row();
        addLabel("Audio", "subtitle").padBottom(10f).colspan(2).row();
        addLabel("Music Volume:", "default").right().padBottom(5f);
        add(musicSlider).fillX().padBottom(5f).row();
        addLabel("SFX Volume:", "default").right().padBottom(5f);
        add(sfxSlider).fillX().padBottom(5f).row();
        add(muteCheck).colspan(2).padBottom(70f).row();
        add(btnClose).colspan(2);
    }
}
