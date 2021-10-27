package com.dylan773.finalyearproject.render.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.render.windows.OptionsWindow;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.AudioController;
import com.dylan773.finalyearproject.EducationGame;
import static com.dylan773.finalyearproject.utilities.Utilities.*;

/**
 * <h1>This application's main menu screen</h1>
 *
 * @author Dylan Brand
 */
//TODO - END the main menu screen when a new game is started (currently keeps playing)
public class MenuScreen extends ScreenAdapter {

    /*
     * Fields
     */
    private Stage stage;
    private Table table;
    private EducationGame game;
    private OptionsWindow optionsWindow = new OptionsWindow();

    /*
     * Constructor
     */
    /**
     * <h2>Main Menu Constructor</h2>
     *
     * @param game The game object passed to this Main Menu screen.
     */
    public MenuScreen(EducationGame game) { // No create method in screen so use a constructor instead
        this.game = game;
        table = new Table();
        table.setFillParent(true);

        stage = new Stage();
        stage.addActor(table);
        stage.addActor(optionsWindow);
        Gdx.input.setInputProcessor(stage); // Enables user input on this stage

        //table.setDebug(true);

        constructContent(); // Constructs the table to be displayed
        AudioController.playMainMenu(); // Plays the main menu music for this application
    }

    /**
     * <h2>Constructs the content to be displayed on this applications main menu</h2>
     * Uses a {@link #table} to arrange actors to be displayed on the main menu screen.
     */
    public void constructContent() {
        table.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.MENU_BACKGROUND)));
        table.add(addLabel("Edu Quiz", "title")).padBottom(20f).row();

        // Play Game Button Controls
        addMenuButton("Play Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelOneScreen(game));
            }
        });

        // Options Button Controls
        addMenuButton("Options").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.setVisible(true); // Displays the options window to the user
                AudioController.playButtonSound();
            }
        });

        // Exit Game Button Controls
        addMenuButton("Exit Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(addLabel("\tCreated by Dylan Brand.\nStudent at De Montfort University.",
                "default")).padTop(20f);
    }

    /**
     * <h2>Instantiates a new TextButton to be used on this application's Main Menu</h2>
     * Creates a new TextButton, setting it's width to 400f, bottom padding of 20f and calls the row() method
     * to separate this button to the next actor. And adds this button to the {@link #table}.
     *
     * @param name The text to be displayed on the TextButton.
     * @return This TextButton.
     */
    private TextButton addMenuButton(String name) {
        TextButton button = new TextButton(name, Assets.SKIN);
        table.add(button).width(400f).padBottom(20f).row();

        return button;
    }

    /**
     * Disables all input events and stops the Menu Screen music when this screen is hidden.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        AudioController.stopNowPlaying();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh
        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();

        // Whilst the options window is visible, if the user has pressed the ESC key, the window will be closed/hidden.
        if (optionsWindow.isVisible()) { // TODO - is this the right place???
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                optionsWindow.setVisible(false);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

//    //TODO - not sure if this is required
//    @Override
//    public void dispose() {
//        stage.dispose();
//    }


}
