package com.dylan773.finalyearproject.render.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.entities.PlayerTest;

/**
 * <h1>Abstract class that provides a base for extending classes to implement a game level</h1>
 * Extending classes inherit all properties and methods, allowing them to share the same functionality,
 * without having to duplicate the logic in each seperate class.
 */
public abstract class GameScene extends ScreenAdapter {

    // FIELDS
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera = new OrthographicCamera(); // Defines the view
    private FillViewport viewport;
    private EducationGame game = new EducationGame();
    private Stage stage;
    private Sprite player;

    private PlayerTest playerTest;

    // METHODS
    /**
     * <h2>TiledMap Renderer and Logic</h2>
     * This method accepts a TiledMap object, assigning logic for that TiledMap to be displayed on the game scene.<br>
     * Extending classes can call this method, providing a TiledMap object to be rendered.
     *
     * Accepts a TiledMap object, rendering that TiledMap.
     * @param tiledMap The TiledMap to be rendered onto the game scene.
     */
    protected void createGameLevel(TiledMap tiledMap, Sprite player) {
        this.player = player; //TODO - set camera to player position

        //TODO - Create a stage with a TiledMap and menu bar
//        stage = new Stage();
//        stage.addActor(menuBar);

        // Create the renderer
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap); // Resize is called after this method, camera is updated there

        // Sets the size of the camera
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera()); // Set the renderer's view to the camera
    }


    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Draws the sprite using the {@link #tiledMapRenderer}.
     * Calls the player's draw method...
     */
    private void drawSprite(Sprite sprite) {
        tiledMapRenderer.getBatch().begin();
        sprite.draw(tiledMapRenderer.getBatch()); // Draws the sprite/player
        //player.setPosition(x, y);
        tiledMapRenderer.getBatch().end();
    }

    @Override
    public void render(float delta) {
        //tiledMapRenderer.getBatch().begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen before rendering the next frame

        tiledMapRenderer.getBatch().setProjectionMatrix(camera.combined);
        //camera.lookAt(playerTest.pos.x, playerTest.pos.y, 0);
        camera.update();

        //tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera()); // Set the renderer's view to the camera
        tiledMapRenderer.setView(camera.combined, (camera.position.x - (camera.viewportWidth * .5f)), (camera.position.y - (camera.viewportHeight * .5f)), camera.viewportWidth, camera.viewportHeight);
        tiledMapRenderer.render(); // renders the map, can also render certain layers

        drawSprite(player); // Draws the sprite for this level
        //tiledMapRenderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        //focusOnPlayer(); // Moves the camera position to the player - move to Render?
    }

    private void focusOnPlayer() {

    }

//    @Override
//    public void hide() {
//        Gdx.input.setInputProcessor(null); // Disable input events when this screen is hidden
//    }

    /**
     * Use this to pause the game when the user views options/how to play?
     */
    @Override
    public void pause() {
        //super.pause();
    }

    /**
     * Resumes the game when the user has closed any in-game window?
     */
    @Override
    public void resume() {
        //super.resume();
    }

    @Override
    public void dispose() {
    }
}



//    public GameScene(EducationGame game) {
//        this.game = game;
//        stage = new Stage();
//        //stage.addActor(menuBar); // TODO - menu bar
//
//        //TODO - enable for user input on this screen
//        //Gdx.input.setInputProcessor(stage); // Set this screen for inputs
//        //focusOnPlayer();
//    }
