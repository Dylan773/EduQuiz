package com.dylan773.finalyearproject.render.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.utilities.Assets;

//TODO - at a future date, set the camera for the player so the entire map isn't visible on screen
public class LevelOneScreen extends ScreenAdapter {
    // Fields
    private TiledMap levelOneMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera = new OrthographicCamera(); // Defines the view
    private FillViewport viewport;

    private EducationGame game;
    private Stage stage;
    private Player player;
    private TiledMap blockedLayer;

    /**
     * <h2>Level One Constructor</h2>
     * @param game
     */
    public LevelOneScreen(EducationGame game) {
        this.game = game;
        stage = new Stage();
        //stage.addActor(menuBar);

        //TODO - enable for user input on this screen
        //Gdx.input.setInputProcessor(stage); // Set this screen for inputs

        constructContent();
        player = new Player(40, 40, Assets.KNIGHT_SPRITE); // x/y = starting position on map
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
        viewport = new FillViewport(1920, 1080, camera);

        //TODO - set camera to player position
    }

//    public void focusOnPlayer() {
//        camera.lookAt(100, 100, 0);
//    }

    /**
     * Draws the sprite using the {@link #tiledMapRenderer}.
     * Calls the player's draw method...
     */
    public void drawSprite() {
        tiledMapRenderer.getBatch().begin();
        player.draw(tiledMapRenderer.getBatch()); // Draws the sprite/player
        //player.setPosition(x, y);
        tiledMapRenderer.getBatch().end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen before rendering the next frame

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Automatically updates the camera
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera()); // Set the renderer's view to the camera
        tiledMapRenderer.render(); // renders the map, can also render certain layers

        drawSprite(); // Draws the sprite for this level
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
//        tiledMapRenderer.dispose(); // these shouldnt be disposed
//        player.getTexture().dispose();
    }
}
