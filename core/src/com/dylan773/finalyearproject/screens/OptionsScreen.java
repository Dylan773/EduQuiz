package com.dylan773.finalyearproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.menuBackground))); // TODO - Change this? dont like the the way this is done

        //constructContent();
        content();
    }

    /**
     *
     * @return
     */
    public Window content() {
        Window optionsWindow = new Window("", Assets.SKIN);
        optionsWindow.setMovable(false);
        optionsWindow.top();

        //TODO - debug
        //optionsWindow.setDebug(true);

        //Option Labels - a lot of em
        Label titleLabel = new Label("OPTIONS", Assets.SKIN, "title");
        Label audioLabel = new Label("Audio", Assets.SKIN, "subtitle");
        Label musicLabel = new Label("Music Volume:", Assets.SKIN, "font", Color.ORANGE);
        Label sfxLabel = new Label("SFX Volume:", Assets.SKIN, "font", Color.ORANGE);
        Label menuExit = new Label("Press ESC to Exit", Assets.SKIN);

        // Game Music Control
        Slider musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        // Game Sound Effect's Control
        Slider sfxSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        // Master Audio Mute
        CheckBox muteCheck = new CheckBox("Mute", Assets.SKIN);

//        optionsWindow.add(titleLabel).padBottom(0f).colspan(2).row();
//        optionsWindow.add(audioLabel).padBottom(10f).colspan(2).row();
//        optionsWindow.add(musicLabel, musicSlider).row();
//        optionsWindow.add(sfxLabel, sfxSlider).row();
//        optionsWindow.add(muteCheck).colspan(2).row();
//        optionsWindow.add(menuExit).colspan(2).padTop(30f);

        // Add actors to the window
        optionsWindow.add(titleLabel).colspan(2).row();
        optionsWindow.add(audioLabel).padBottom(10f).colspan(2).row();
        optionsWindow.add(musicLabel).padBottom(5f);
        optionsWindow.add(musicSlider).padBottom(5f).row();
        optionsWindow.add(sfxLabel, sfxSlider).row();
        optionsWindow.add(muteCheck).colspan(2).row();
        optionsWindow.add(menuExit).colspan(2).padTop(30f);

        rootTable.add(optionsWindow).size(1000f, 600f);
        return optionsWindow;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh
        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            game.setScreen(new MenuScreen(game));
        //super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

//    public void constructContent() {
//        // Initialising Game Label's'
//        Label audioLabel = new Label("Audio", Assets.SKIN);
//        Label musicLabel = new Label("Music Volume:", Assets.SKIN);
//        Label sfxLabel = new Label("SFX Volume:", Assets.SKIN);
//        Label muteLabel = new Label("Mute:", Assets.SKIN);
//
//        // Music volume control
//        Slider musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
//        //musicSlider.setValue();
//
//        // Sound Effect volume control
//        Slider sfxSlider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.SKIN);
//        //sfxSlider.setValue();
//
//        CheckBox muteCheck = new CheckBox("", Assets.SKIN);
//
//        TextButton button = new TextButton("Return", Assets.SKIN);
//        //button.getLabel().setFontScale(0.5f);
//        button.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new MenuScreen(game));
//            }
//        });
//
//        // Add actors to the table
//        rootTable.add(audioLabel).colspan(2).pad(20f, 0f, 20f, 0f).row();
//        rootTable.add(musicLabel, musicSlider).padBottom(10).row();
//        rootTable.add(sfxLabel, sfxSlider).row();
//        rootTable.add(muteLabel, muteCheck).row();
//        rootTable.add(button);
//
//        // Table Design
//        rootTable.center().top();
//        rootTable.setDebug(true);
//        //rootTable.pack();
//    }