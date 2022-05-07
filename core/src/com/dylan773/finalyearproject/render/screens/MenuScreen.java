package com.dylan773.finalyearproject.render.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.render.windows.*;
import com.dylan773.finalyearproject.utilities.AudioController;

import static com.dylan773.finalyearproject.render.windows.LevelSelector.getLevelList;
import static com.dylan773.finalyearproject.utilities.Assets.*;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;
import static com.dylan773.finalyearproject.utilities.Utilities.*;

/**
 * <h1>This application's main menu screen</h1>
 *
 * @author Dylan Brand
 */
public class MenuScreen extends ScreenAdapter {

    /*
     * Fields
     */
    private Stage stage;
    private Table table, infoButtonTable;


    /**
     * <h2>Constructor</h2>
     */
    public MenuScreen() {
        table = new Table();
        table.setFillParent(true);

        infoButtonTable = new Table();
        infoButtonTable.setFillParent(true);

        stage = new Stage();
        stage.addActor(table);
        stage.addActor(infoButtonTable);

        Gdx.input.setInputProcessor(stage); // Enables user input on this stage

        initialiseScreen(); // Constructs the table to be displayed
        AudioController.playMainMenu(); // Plays the main menu music on loop

        // Clears the previously loaded game levels everytime the user visits this main menu
        getLevelList().clear();
    }


    /**
     * <h2>Constructs the content to be displayed on this applications main menu</h2>
     * Uses a {@link #table} to arrange actors to be displayed on the main menu screen.
     */
    public void initialiseScreen() {
        table.setBackground(new TextureRegionDrawable(new TextureRegion(MAIN_MENU_BACKGROUND)));
        table.add(addLabel("Edu Quiz", "title", Color.WHITE)).padBottom(20f).row();

        // Play Game Button Controls
        addMenuButton("Play Game").addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                stage.addAction(Actions.sequence(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        stage.addActor(new LevelSelector());
                    }
                }), Actions.fadeIn(1)));
            }
        });

        // Options Button Controls
        addMenuButton("Options").addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                playButtonSound();
                stage.addActor(new OptionsWindow());
            }
        });

        // Exit Game Button Controls
        addMenuButton("Exit Game").addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // Game Author Label
        table.add(addLabel("Created by Dylan Brand.\nStudent at De Montfort University.",
                "subtitle", Color.CORAL)).padTop(20f);

        // Game Info Button
        infoButtonTable.add(gameInfoButton()).pad(40f).expand().bottom().right();
    }


    /**
     * <h2>Instantiates a new TextButton to be used on this application's Main Menu</h2>
     * Creates a new TextButton, setting it's width to 400f, bottom padding of 20f and calls .row()
     * to separate this button to the next actor. And adds this button to the {@link #table}.
     *
     * @param name The text to be displayed on the TextButton.
     * @return The TextButton.
     */
    private TextButton addMenuButton(String name) {
        TextButton button = new TextButton(name, SKIN);
        table.add(button).width(400f).padBottom(20f).row();
        return button;
    }


    /**
     * Disables all input events and stops the Menu Screen music when this screen is hidden.
     */
    @Override
    public void hide() {

    }


    /**
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh
        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    /**
     * A TextButton that will display a game info window once clicked.
     */
    private TextButton gameInfoButton() {
        TextButton infoButton = new TextButton("?", SKIN);
        infoButton.setSize(100f, 100f);
        infoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Gdx.app.exit();
            }
        });

        return infoButton;
    }
}
