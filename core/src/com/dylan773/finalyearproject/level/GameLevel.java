package com.dylan773.finalyearproject.level;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.render.windows.GameBar;
import com.dylan773.finalyearproject.render.windows.QuestionWindow;
import com.dylan773.finalyearproject.render.windows.RestartLevel;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.Utilities;

import java.util.ArrayList;
import java.util.Objects;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playLevelTheme;
import static com.dylan773.finalyearproject.utilities.Utilities.*;

/**
 * Abstract view of a level.
 * <p>
 * It can display and handle player movement, objects and rendering
 * of any tmx valid for this game.
 *
 * @see LevelFactory Level Factory for creation of a level.
 */
public class GameLevel extends ScreenAdapter {

    // FIELDS
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private OrthographicCamera camera;
    private FillViewport viewport;
    private InputAdapter keyListener;
    private GameBar gameBar = new GameBar();

    private static Stage stage;
    private static ProgressBar healthBar;
    private static Sprite heart = Assets.spritesheet.createSprite("love_heart");

    protected TiledMap map;
    protected World world;
    protected RectangleMapObject spawn;
    protected RectangleMapObject exit;
    protected InputMultiplexer inputHandlers = new InputMultiplexer();
    protected Player player;

    // TODO - can these be converted to private?
    public Float desiredCamZoom = .5f;
    public Vector3 desiredCamPos = new Vector3();
    public boolean renderHitBoxes = false;
    public int questionIndex = 0;
    public static LevelFactory.Level currentLevel;

    Table table; // the table only has the text in
    Table tableTopRow;

    //#region construction

    /**
     * @param mapPath
     */
    public GameLevel(String mapPath, LevelFactory.Level level) {
        loadWorld(mapPath);
        playerInit();
        renderInit();
        stageInit();
        inputInit();
        collisionInit();
//        initEndZone(); // TODO - change this implementation


        currentLevel = level;

        Assets.questions.shuffleLevels();

        playLevelTheme(this);
    }





    /**
     * Loads the map and stores it in {@link GameLevel#map}
     *
     * @param mapPath The path of the map to load
     */
    private void loadWorld(String mapPath) {
        map = new TmxMapLoader().load(mapPath);
        world = new World(new Vector2(0, 0), true);

        spawn = Objects.requireNonNull(
                (RectangleMapObject) map.getLayers().get("objects").getObjects().get("spawn"),
                "The world loaded did not have a spawnpoint!"
        );

        exit = Objects.requireNonNull(
                (RectangleMapObject) map.getLayers().get("objects").getObjects().get("exit"),
                "The world loaded did not have an exit point."
        );
    }

    /**
     * Creates the player
     */
    private void playerInit() {
        player = new Player(this);
        playerToSpawn();
    }

    /**
     *
     */
    private void stageInit() {
        stage = new Stage();

        // Table config
        table = new Table();
        table.setVisible(false);
        table.setFillParent(true);

        // Table top row config
        tableTopRow = new Table();
        tableTopRow.setFillParent(true);

        // Label config
        Label pause = new Label("Paused\nESC to resume", SKIN, "subtitle", Color.ORANGE);
        pause.setAlignment(Align.center);
        table.add(pause).padBottom(Value.percentHeight(10f)); // addLabel??

        Label lblMenu = new Label("Menu (ESC)", SKIN, "subtitle", Color.ORANGE); // same here?

        // Health Bar inti
        healthBar = new ProgressBar(0f, 12f, 3f, false, SKIN);
        healthBar.setValue(12f);

        // Top of stage icons
        tableTopRow.add(lblMenu).pad(50f, 0f, 0f, Gdx.graphics.getWidth() * 0.4f);
        tableTopRow.add(healthBar).width(400f).padTop(50f);
        tableTopRow.top();

        // Adding the actors to the stage.
        stage.addActor(gameBar);
        stage.addActor(table);
        stage.addActor(tableTopRow);

        // TODO -  figure a better way to do this
        heart.setPosition(Gdx.graphics.getWidth() * 0.64f, Gdx.graphics.getHeight() * 0.93f);
    }


    // TODO - move
    //=====================================

    public static void decreasePlayerLives(Actor actor) {
        if ((healthBar.getValue() - healthBar.getStepSize()) == 0f) {
            healthBar.setValue(0f);
            stage.addActor(new RestartLevel());
            stage.getActors().removeValue(actor, true);
        } else healthBar.setValue(healthBar.getValue() - healthBar.getStepSize());
    }


    //======================================

    /**
     *
     */
    private void collisionInit() {
        // Box2D variables
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for (RectangleMapObject object : map.getLayers().get("trigger-zone").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // TODO - detect collision with end zone

                stage.addActor(new QuestionWindow());
                // TODO - look at postRunnable and fully understand + box2d contacts
                if (contact.getFixtureA() != player.playerFixture)
                    Gdx.app.postRunnable(() -> contact.getFixtureA().getBody().destroyFixture(contact.getFixtureA()));
                else
                    Gdx.app.postRunnable(() -> contact.getFixtureB().getBody().destroyFixture(contact.getFixtureB()));
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

        });
    }


    /**
     *
     */
    private void inputInit() {
        inputHandlers.addProcessor(stage);

        keyListener = new InputAdapter() {
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
                        gameBar.setVisible(!gameBar.isVisible()); //Invert settings - look into that
                        table.setVisible(gameBar.isVisible());

                        if (gameBar.isVisible()) pause();
                        else resume();

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

                    default:
                        return super.keyTyped(character);
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
                desiredCamZoom = clamp(0.1f, 0.8f, camera.zoom + (amountY * 0.1f)); // amount reduced to reduce how much it zooms when scrolling
                return true;    // Indicate that the scroll has been handled.
            }
        };
        enableUserInput();
        Gdx.input.setInputProcessor(inputHandlers);
    }
    //#endregion construction


    //#region general methods

    public void disableUserInput() {
        inputHandlers.removeProcessor(keyListener);
        player.pauseMovement();
    }

    /**
     *
     */
    public void enableUserInput() {
        inputHandlers.addProcessor(keyListener);
        player.resumeMovement();
    }

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

        // TODO - look this up
        // RGB number / 255
        Gdx.gl.glClearColor(0.07843137255f, 0.04705882353f, 0.1098039216f, 0f);

        // Create the renderer
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

        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();

        debugRenderer.render(world, getCamera().combined);

        // TODO - this isnt ideal? being rendered every frame
        stage.getBatch().begin();
        heart.draw(stage.getBatch());
        stage.getBatch().end();
    }


    /**
     *
     */
    private void renderWorld() {
        tiledMapRenderer.setView(camera.combined, (camera.position.x - (camera.viewportWidth * .5f)), (camera.position.y - (camera.viewportHeight * .5f)), camera.viewportWidth, camera.viewportHeight);
        // renders the map, can also render certain layers
        tiledMapRenderer.render();
    }

    /**
     *
     */
    private void renderPlayer() {
        drawSprite(player);
    }

    private void renderHeartIcon() {

    }

    /**
     * why do we need this??? research it
     */
    private void processCollisions() {
        world.step(1 / 60f, 6, 2);
    }


    /**
     *
     */
    private void renderDebug() {
        if (renderHitBoxes)
            debugRenderer.render(world, getCamera().combined);
    }


    /**
     *
     */
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

        stage.getBatch().begin();
        heart.draw(stage.getBatch());
        stage.getBatch().end();
    }


    /**
     * Use this to pause the game when the user views options/how to play?
     */
    @Override
    public void pause() {
        player.pauseMovement();
    }

    /**
     * Resumes the game when the user has closed any in-game window?
     */
    @Override
    public void resume() {
        player.resumeMovement();
    }

    @Override
    public void dispose() {
        tiledMapRenderer.dispose();
        map.dispose();
        world.dispose();
    }

    //#endregion
    //#region get

    public static Stage getStage() {
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

    public static LevelFactory.Level getCurrentLevel() {
        return currentLevel;
    }

    public void disposeLevel() {
        this.dispose();
    }

    //#endregion get
}