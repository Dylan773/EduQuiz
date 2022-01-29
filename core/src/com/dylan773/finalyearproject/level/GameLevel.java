package com.dylan773.finalyearproject.level;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.render.screens.MenuScreen;
import com.dylan773.finalyearproject.render.windows.GameBar;
import com.dylan773.finalyearproject.utilities.Utilities;

import java.io.InputStream;
import java.util.Objects;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Utilities.clamp;
import static com.dylan773.finalyearproject.utilities.Utilities.lerp;

/**
 * Abstract view of a level.
 *
 * It can display and handle player movement, objects and rendering
 * of any tmx valid for this game.
 *
 * @see LevelFactory Level Factory for creation of a level.
 */
public class GameLevel extends ScreenAdapter {

    // FIELDS
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public boolean renderHitBoxes = false;

    private OrthographicCamera camera;
    public Vector3 desiredCamPos = new Vector3();
    public Float desiredCamZoom = .5f;

    private FillViewport viewport;

    private Stage stage;
    Table table;
    private GameBar gameBar = new GameBar();

    protected TiledMap map;
    protected World world;
    protected RectangleMapObject spawn;

    protected InputMultiplexer inputHandlers = new InputMultiplexer();

    protected Player player;


    //#region construction
    public GameLevel(String mapPath) {
        loadWorld(mapPath);
        playerInit();
        renderInit();
        stageInit();
        inputInit();
    }

    /**
     * Loads the map and stores it in {@link GameLevel#map}
     * @param mapPath The path of the map to load
     */
    private void loadWorld(String mapPath) {
        map = new TmxMapLoader().load(mapPath);
        world = new World(new Vector2(0, 0), true);

        spawn = Objects.requireNonNull(
                (RectangleMapObject) map.getLayers().get("objects").getObjects().get("spawn"),
                "The world loaded did not have a spawnpoint!"
        );
    }

    /**
     * Creates the player
     */
    private void playerInit() {
        player = new Player(this);
        playerToSpawn();
    }

    private void stageInit() {
        stage = new Stage();
        //stage.addActor(menuBar); // TODO - menu bar
    }

    private void inputInit() {
        inputHandlers.addProcessor(stage);

        inputHandlers.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        Utilities.debugMod(0.1f);
                        break;
                    case Input.Keys.DOWN:
                        Utilities.debugMod(-0.1f);
                        break;
                    case Input.Keys.ESCAPE:
                        CLIENT.setScreen(new MenuScreen());
                        break;
                }

                return super.keyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode) {
                return super.keyUp(keycode);
            }

            @Override
            public boolean keyTyped(char character) {
                switch (character) {
                    case 'r':
                        playerToSpawn();
                        break;

                    default: return super.keyTyped(character);
                }

                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return super.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                desiredCamZoom = clamp(0.1f,0.8f, camera.zoom + (amountY * 0.1f)); // amount reduced to reduce how much it zooms when scrolling
                return true;    // Indicate that the scroll has been handled.
            }
        });

        Gdx.input.setInputProcessor(inputHandlers);
    }
    //#endregion construction


    //#region general methods
    /**
     * Moves the player to {@link GameLevel#world}
     */
    public void playerToSpawn() {
        spawn.getRectangle().getPosition(player.pos);
    }

    //#endregion
    //#region rendering

    /**
     * <h2>Configures the world for rendering.</h2>
     * Accepts a TiledMap object, and configures the {@link GameLevel#camera},
     * {@link GameLevel#viewport}, and {@link GameLevel#tiledMapRenderer}
     * in order to render it.
     */
    private void renderInit() {
        //TODO - Create a stage with a TiledMap and menu bar

        // Create the renderer
        // Resize is called after this method, camera is updated there
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        // Configure the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.7f; // Sets the zoom of the game camera

        // Assigns the viewport with the width/height of the screen and the camera
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        // Set the renderer's view to the camera
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera());
        tiledMapRenderer.getBatch().setProjectionMatrix(camera.combined);
    }

    /**
     * Draws the sprite using the {@link #tiledMapRenderer}.
     * Calls the player's draw method...
     */
    private void drawSprite(Sprite sprite) {
        //TODO ideally, begin / end should only be called once per frame.
        //      If multiple sprites were drawn, it would restart the sprite batch.
        tiledMapRenderer.getBatch().begin();
        sprite.draw(tiledMapRenderer.getBatch());
        tiledMapRenderer.getBatch().end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen before rendering the next frame

        camera.update();
        renderWorld();
        renderPlayer();
        renderDebug();

        processCollisions();
        processCameraMovement();
    }

    private void renderWorld() {
        tiledMapRenderer.setView(camera.combined, (camera.position.x - (camera.viewportWidth * .5f)), (camera.position.y - (camera.viewportHeight * .5f)), camera.viewportWidth, camera.viewportHeight);
        // renders the map, can also render certain layers
        tiledMapRenderer.render();
    }

    private void renderPlayer() {
        drawSprite(player);
    }

    private void processCollisions() {
        world.step(1 / 60f, 6, 2);
    }

    private void renderDebug() {
        if (renderHitBoxes)
            debugRenderer.render(world, getCamera().combined);
    }

    private void processCameraMovement() {
        desiredCamPos.set(player.pos, 0);
        // linear interoperation - Moves the camera towards the desired position by a percentage every frame.
        camera.position.lerp(desiredCamPos, 0.08f);
        camera.zoom = lerp(camera.zoom, desiredCamZoom, 0.1f);
    }
    //#endregion
    //#region engine screen calls

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }


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
       tiledMapRenderer.dispose();
       map.dispose();
       world.dispose();
    }

    //#endregion
    //#region get

    public Stage getStage() {
        return stage;
    }

    public Vector3 getDesiredCamPos() {
        return desiredCamPos;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public RectangleMapObject getSpawn() {
        return spawn;
    }

    public Player getPlayer() {
        return player;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
    //#endregion get
}