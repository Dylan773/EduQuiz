package com.dylan773.finalyearproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.Assets;
import com.dylan773.finalyearproject.EducationGame;

//TODO - is it worth extending Window???????

/**
 *
 */
public class OptionsScreen extends ScreenAdapter {
    private EducationGame game;
    private Stage stage;
    private Table rootTable;

    // Constructor
    /**
     *
     * @param game
     */
    public OptionsScreen(EducationGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        constructContent();
    }

    /**
     *
     */
    public void constructContent() {
        // Initialising Game Label's'
        Label audioLabel = new Label("Audio", Assets.SKIN);
        Label musicLabel = new Label("Music Volume:", Assets.SKIN);
        Label sfxLabel = new Label("SFX Volume:", Assets.SKIN);
        Label muteLabel = new Label("Mute:", Assets.SKIN);

        // Music volume control
        Slider musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
        //musicSlider.setValue();

        // Sound Effect volume control
        Slider sfxSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
        //sfxSlider.setValue();

        CheckBox muteCheck = new CheckBox("", Assets.SKIN);

        TextButton button = new TextButton("Return", Assets.SKIN);
        //button.getLabel().setFontScale(0.5f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });

        // Add actors to the table
        rootTable.add(audioLabel).colspan(2).pad(20f, 0f, 20f, 0f).row();
        rootTable.add(musicLabel, musicSlider).padBottom(10).row();
        rootTable.add(sfxLabel, sfxSlider).row();
        rootTable.add(muteLabel, muteCheck).row();
        rootTable.add(button);

        // Table Design
        rootTable.center().top();
        rootTable.setDebug(true);
        //rootTable.pack();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh
        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();
        //super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void windowTest() {

    }

}

// Events
//        musicSlider.addListener(new ChangeListener() {
//@Override
//public void changed(ChangeEvent event, Actor actor) {
//
//        }
//        });
//
//        sfxSlider.addListener(new ChangeListener() {
//@Override
//public void changed(ChangeEvent event, Actor actor) {
//
//        }
//        });