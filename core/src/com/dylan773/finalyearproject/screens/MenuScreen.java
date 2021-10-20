package com.dylan773.finalyearproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.Assets;
import com.dylan773.finalyearproject.EducationGame;

/**
 *
 */

//TODO - Change title font and colour
public class MenuScreen extends ScreenAdapter {


    // Fields
    private Stage stage;
    private Table table;
    private final EducationGame game;
    private OptionWindow optionWindow = new OptionWindow("", Assets.SKIN);

    // Constructor
    /**
     * @param game
     */
    public MenuScreen(EducationGame game) {
        this.game = game;

        table = new Table();
        table.setFillParent(true);

        stage = new Stage();
        stage.addActor(table);
        stage.addActor(optionWindow);
        Gdx.input.setInputProcessor(stage);

        // TODO - debug
        //table.setDebug(true);

        constructContent();
    }

    /**
     *
     */
    public void constructContent() {
        table.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.menuBackground)));

        // Title Label
        Label gameLabel = new Label("Edu Quiz", Assets.SKIN, "title");
        table.add(gameLabel).pad(0, 0, 20, 0).row();

        // Play Game Button
        addButton("Play Game").addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                game.setScreen(new OptionsScreen(game));
//            }
        });

        // Option Window Button
        addButton("Options").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new OptionsScreen(game));
                optionWindow.setVisible(true);
            }
        });

        // Exit Game Button
        addButton("Exit Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    /**
     * @param name
     * @return
     */
    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, Assets.SKIN);
        table.add(button).width(400f).padBottom(20f).row();

        //TODO - button sound
        return button;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh
        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();

        // Checks for keyboard input if the options window is currently visible on the stage.
        if (optionWindow.isVisible()) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                optionWindow.setVisible(false);
        }

//        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // surprised this worked
    }

    @Override
    public void dispose() {
        //stage.dispose();
    }

    /**
     *
     */
    private static class OptionWindow extends Window { //TODO - Set the values of the sliders and checkbox to the same value as the audio, so they dont reset back to 0 each time
        public OptionWindow(String title, Skin skin) {
            super(title, skin);
            this.setResizable(false);
            this.setMovable(false);
            this.setVisible(false); // Initially the window is hidden
            this.setSize(1000f, 600f);
            //this.top();
            this.setPosition(150, 50); // TODO - I dont like this hard coded, center using a different method

            Label titleLabel = new Label("OPTIONS", Assets.SKIN, "title");
            Label audioLabel = new Label("Audio", Assets.SKIN, "subtitle");
            Label musicLabel = new Label("Music Volume:", Assets.SKIN);
            Label sfxLabel = new Label("SFX Volume:", Assets.SKIN);
            Label menuExit = new Label("Press ESC to Exit", Assets.SKIN, "font", Color.ORANGE);


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

            // Add each actor to the window
            this.add(titleLabel).colspan(2).row();
            this.add(audioLabel).padBottom(10f).colspan(2).row();
            this.add(musicLabel).padBottom(5f);
            this.add(musicSlider).padBottom(5f).row();
            this.add(sfxLabel, sfxSlider).row();
            this.add(muteCheck).padBottom(157f).colspan(2).row();
            this.add(menuExit).colspan(2);
        }
    }
}
