package com.dylan773.finalyearproject.render.screens;

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
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.AudioController;
import com.dylan773.finalyearproject.utilities.EducationGame;

import static com.dylan773.finalyearproject.utilities.Utilities.*;

/**
 *
 */

//TODO - END the main menu screen when a new game is started (currently keeps playing)
public class MenuScreen extends ScreenAdapter {
    // Fields
    private final Stage stage;
    private final Table table;
    private EducationGame game;
    private final OptionWindow optionWindow = new OptionWindow("", Assets.SKIN);

    // Constructor

    /**
     * @param game
     */

    // No create method in screen so use a constructor instead
    public MenuScreen(EducationGame game) {
        this.game = game;
        table = new Table();
        table.setFillParent(true);

        stage = new Stage();
        stage.addActor(table);
        stage.addActor(optionWindow);
        Gdx.input.setInputProcessor(stage);

        AudioController.playMainMenu(); // Plays the main menu music for this application
        //table.setDebug(true);

        constructContent();
    }

    /**
     *
     */
    public void constructContent() {
        table.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.MENU_BACKGROUND)));
        table.add(addLabel("Edu Quiz", "title")).padBottom(20f).row();

        // Play Game Button Controls
        addMenuButton("Play Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO - setScreen doesnt dispose the menu screen
                game.setScreen(new GameScreen(game));
                //AudioController.stopNowPlaying();
                //Gdx.input.setInputProcessor(null);
            }
        });

        // Options Button Controls
        addMenuButton("Options").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionWindow.setVisible(true);
                AudioController.playButtonSound();
            }
        });

        // Exit Game Button Controls
        addMenuButton("Exit Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO - setScreen doesnt dispose the menu screen
                Gdx.app.exit();
            }
        });
    }


    /**
     * <h2>Instantiates a new TextButton to be used on this applications Main Menu</h2>
     * Creates a new TextButton, setting it's width to 400f, bottom padding of 20f and calls the row() method
     * to separate this button to the next actor.
     *
     * @param name The text (String) to be displayed on the TextButton.
     * @return This TextButton.
     */
    private TextButton addMenuButton(String name) {
        TextButton button = new TextButton(name, Assets.SKIN);
        table.add(button).width(400f).padBottom(20f).row();

        return button;
    }


    @Override
    public void hide() {
        //super.hide();
        AudioController.stopNowPlaying();
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
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // surprised this worked
    }

//    //TODO - work out if this is required
//    @Override
//    public void dispose() {
//        stage.dispose();
//    }

    /**
     *
     */
    //TODO - move to seperate class
    private static class OptionWindow extends Window {
        OptionWindow(String title, Skin skin) {
            super(title, skin);
            this.setResizable(false);
            this.setMovable(false);
            this.setVisible(false); // Initially the window is hidden
            this.setSize(1000f, 600f);
            //this.top();
            this.setPosition(150, 50); // TODO - I dont like this hard coded, center using a different method

            // TODO - Create a addLabel method to remove this
            Label titleLabel = new Label("OPTIONS", Assets.SKIN, "title");
            Label audioLabel = new Label("Audio", Assets.SKIN, "subtitle");
            Label musicLabel = new Label("Music Volume:", Assets.SKIN);
            Label sfxLabel = new Label("SFX Volume:", Assets.SKIN);
            Label menuExit = new Label("Press ESC to Exit", Assets.SKIN, "font", Color.ORANGE);


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

            // Master Audio Mute
            final CheckBox muteCheck = new CheckBox("Mute", Assets.SKIN);
            muteCheck.setChecked(AudioController.getIsMuted());
            muteCheck.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    AudioController.setMute(muteCheck.isChecked());
                }
            });

            // Add each actor to the window
            //TODO - cant add on same row if i want to change size
            this.add(titleLabel).colspan(2).row();
            this.add(audioLabel).padBottom(10f).colspan(2).row();
            this.add(musicLabel).right().padBottom(5f);
            this.add(musicSlider).fillX().padBottom(5f).row();
            this.add(sfxLabel).right().padBottom(5f);
            this.add(sfxSlider).fillX().padBottom(5f).row();
            this.add(muteCheck).padBottom(157f).colspan(2).row();
            this.add(menuExit).colspan(2);
        }
    }
}
