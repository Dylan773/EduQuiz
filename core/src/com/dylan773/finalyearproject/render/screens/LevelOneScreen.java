package com.dylan773.finalyearproject.render.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.utilities.EducationGame;

public class LevelOneScreen extends ScreenAdapter {
    // Fields
    private TiledMap levelOne;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;

    private EducationGame game;
    private final Stage stage;
    private Player player;
    //private MenuBar menuBar = new MenuBar();
    //private final Table table;

    public LevelOneScreen(EducationGame game) {
        this.game = game;
        stage = new Stage();
        //stage.addActor(menuBar);
        //TODO - works here too
        //levelOne = new TmxMapLoader().load("levels/one.tmx");

        //TODO - enable for user input on this screen
        //Gdx.input.setInputProcessor(stage); // Set this screen for inputs
    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(); // renders everything, can also render certain layers
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show() {
        // Load the map
        levelOne = new TmxMapLoader().load("levels/one.tmx");

        // Create the renderer
        tiledMapRenderer = new OrthogonalTiledMapRenderer(levelOne);

        camera = new OrthographicCamera(); // Resize is called after this method, camera is updated there

        // TODO - add player class
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // Disable input events when this screen is hidden
    }

    @Override
    public void pause() {
        //super.pause();
    }

    @Override
    public void resume() {
        //super.resume();
    }

    @Override
    public void dispose() {
        // Dispose all resources once done
//        levelOne.dispose();
//        renderer.dispose();
    }
}
