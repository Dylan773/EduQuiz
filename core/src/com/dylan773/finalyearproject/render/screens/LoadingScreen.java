package com.dylan773.finalyearproject.render.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.level.LevelFactory;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.render.windows.LevelSelector.getLevelsIterated;
import static com.dylan773.finalyearproject.utilities.Assets.LOADING_SCREEN_BACKGROUND;
import static com.dylan773.finalyearproject.utilities.Utilities.addLabel;

/**
 * A loading screen for in-game level transitioning.
 *
 * @author Dylan Brand
 */
public class LoadingScreen extends ScreenAdapter {

    private Stage stage;
    private Table table;

    /** The time (in seconds) this screen has been visible to the user. */
    private float elapsedTime;


    /** Constructor */
    public LoadingScreen() {
        initialiseScreen();
    }


    /** Content for this LoadingScreen. */
    private void initialiseScreen() {
        // Initialise table for content
        table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(LOADING_SCREEN_BACKGROUND)));

        // Adding the table to the stage
        stage = new Stage();
        stage.addActor(table);

        // Info labels
        table.add(addLabel("Loading the next level.", "title", Color.CORAL)).row();
        table.add(addLabel("<-------------->", "title", Color.CORAL));
    }


    private static void loadingEvent() {
//        new DelayEvent(1000, () -> CLIENT.setScreen(LevelFactory.newLevel(getLevelsIterated().next())));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh
        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();

        elapsedTime += delta; // this is not ideal
        // extract to its own method?
        if (elapsedTime > 2.5) {
            CLIENT.setScreen((LevelFactory.newLevel(getLevelsIterated().next())));
            dispose();
        }
    }

    @Override
    public void dispose() {
        stage.clear(); // is this necessary?
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
