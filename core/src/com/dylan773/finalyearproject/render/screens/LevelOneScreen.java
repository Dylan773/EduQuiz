package com.dylan773.finalyearproject.render.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.EducationGame;

public class LevelOneScreen extends ScreenAdapter {
    // Fields
    private TiledMap levelOneMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera = new OrthographicCamera(); // Defines the view

    private FitViewport viewport;

    private EducationGame game;
    private final Stage stage;
    //private Player player;


    public LevelOneScreen(EducationGame game) {
        this.game = game;
        stage = new Stage();
        //stage.addActor(menuBar);

        //TODO - enable for user input on this screen
        //Gdx.input.setInputProcessor(stage); // Set this screen for inputs

        constructContent();
        //player = new Player();
        //focusOnPlayer();
    }

    /**
     *
     */
    public void constructContent() {
        // Load the map
        levelOneMap = new TmxMapLoader().load("levels/one.tmx");
        // Create the renderer
        tiledMapRenderer = new OrthogonalTiledMapRenderer(levelOneMap);
        // Resize is called after this method, camera is updated there

        // Sets the size of the camera
        camera.setToOrtho(false, 1920, 1080);
        viewport = new FitViewport(1920, 1080, camera);


        //TODO - set camera to player position
        //Gdx.input.setInputProcessor(player); // Sets the input to the player
    }

//    public void focusOnPlayer() {
//        camera.lookAt(100, 100, 0);
//    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen before rendering the next frame
        //Gdx.gl.glClearColor(0, 0, 0, 1);

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Automatically updates the camera //camera.update();
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera()); // Set the renderer's view to the camera
        tiledMapRenderer.render(); // renders everything, can also render certain layers
//        // Player
//        tiledMapRenderer.getBatch().begin();
//        //player.setPosition(playerX, playerY);
//        player.draw(tiledMapRenderer.getBatch()); // Draws the player
//        tiledMapRenderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        //focusOnPlayer(); // Moves the camera position to the player
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
        levelOneMap.dispose();
        tiledMapRenderer.dispose();
        //player.getTexture().dispose();
    }
}
