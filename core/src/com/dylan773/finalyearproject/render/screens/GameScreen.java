package com.dylan773.finalyearproject.render.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.EducationGame;

public class GameScreen extends ScreenAdapter {
    // Fields
    private TiledMap levelOne, levelTwo, levelThree;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private EducationGame game;
    private final Stage stage;
    //private final Table table;

    public GameScreen(EducationGame game) {
        this.game = game;
        stage = new Stage();
        //TODO - enable for user input on this screen
        //Gdx.input.setInputProcessor(stage); // Set this screen for inputs
    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh

        renderer.setView(camera);
        renderer.render(); // renders everything, can also render certain layers


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
        levelOne = new TmxMapLoader().load("maps/level one/map.tmx");

        // Create the renderer
        renderer = new OrthogonalTiledMapRenderer(levelOne);

        camera = new OrthographicCamera();
    }

    @Override
    public void hide() {

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
//        levelTwo.dispose();
//        levelThree.dispose();
//        renderer.dispose();
    }
}
