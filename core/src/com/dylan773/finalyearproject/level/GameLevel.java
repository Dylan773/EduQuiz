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
import com.dylan773.finalyearproject.render.screens.LoadingScreen;
import com.dylan773.finalyearproject.render.screens.MenuScreen;
import com.dylan773.finalyearproject.render.windows.*;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.Utilities;

import java.util.Objects;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.render.windows.LevelSelector.getLevelsIterated;
//import static com.dylan773.finalyearproject.render.windows.RestartLevelKt.disposeCurrentLevel;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playIncorrectAns;
import static com.dylan773.finalyearproject.utilities.AudioController.playLevelTheme;
import static com.dylan773.finalyearproject.utilities.Utilities.*;

/**
 * Abstract view of a level.
 * <p>
 * It can display and handle player movement, objects and rendering
 * of any tmx valid for this game.
 *
 * @author Dylan Brand
 * @see LevelFactory Level Factory for creation of a level.
 */
public class GameLevel extends ScreenAdapter {

    // FIELDS
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private OrthographicCamera camera;
    private FillViewport viewport;
    private InputAdapter keyListener;
    private GamePausedWindow gamePausedWindow = new GamePausedWindow();

    private static Stage stage;
    private static ProgressBar healthBar;

    protected static TiledMap map;
    protected static World world;

    protected RectangleMapObject spawn;
    protected RectangleMapObject exit;
    protected InputMultiplexer inputHandlers = new InputMultiplexer();
    protected Player player;

    public Float desiredCamZoom = .3f;
    public Vector3 desiredCamPos = new Vector3();
    public boolean renderHitBoxes = false;
    public int questionIndex = 0;
    public static LevelFactory.Level currentLevel;

    Table table; // the table only has the text in
    Table tableTopRow;
    Fixture endZoneFixture;

    //#region construction

    /**
     * <h1>Game Scene Constructor.</h1>
     *
     * @param mapPath the path location of the TMX TileMap to be loaded.
     * @param level   The enum value of the level to be instantiated.
     */
    public GameLevel(String mapPath, LevelFactory.Level level) {
        currentLevel = level;

        loadWorld(mapPath); // obtains the file location of
        playerInit();
        renderInit();
        stageInit();
        inputInit();
        collisionInit();
        endZoneInit(); //hmmm, this needs replacing?

        showGameInfo(); // Determines the visibility of the level info window.

        Assets.questions.shuffleLevels(); // Shuffle the loaded level's questions
        playLevelTheme(this);
    }


    /**
     * Loads the map and stores it in the {@link GameLevel#map} instance
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
    }


    /**
     * {@link #player} initialisation
     */
    private void playerInit() {
        player = new Player(this);
        playerToSpawn();
    }


    /**
     * {@link #stage} initialisation
     */
    private void stageInit() {
        stage = new Stage();

        // Table config for the game paused label
        table = new Table();
        table.setVisible(false);
        table.setFillParent(true);

        // Table config for the top row
        tableTopRow = new Table();
        tableTopRow.setFillParent(true);

        // Adding the label to the table
        Label pause = new Label("Paused\nESC to resume", SKIN, "subtitle", Color.ORANGE);
        pause.setAlignment(Align.center);
        table.add(pause).padBottom(Value.percentHeight(5f)); // addLabel??

        // Health bar init
        healthBar = new ProgressBar(0f, 12f, 3f, false, SKIN);
        healthBar.setValue(12f);
        healthBar.setAnimateDuration(1f);

        // Top row init
        tableTopRow.add(addLabel("Menu (ESC)", "subtitle", Color.WHITE)).expandX().right();
        tableTopRow.add(addLabel("<-" + currentLevel.name().toUpperCase() + "->", "subtitle", Color.WHITE)).center().expandX();
        tableTopRow.add(healthBar).width(300f).expandX().left().row();
        tableTopRow.add(addLabel("|-------------------------------------------------------|", "subtitle", Color.ORANGE)).colspan(3).fillX();
        tableTopRow.top().padTop(35f); // sets the alignment (top) of this table in it's

        // Adding the actors to the stage.
        stage.addActor(tableTopRow);
        stage.addActor(table);
        stage.addActor(gamePausedWindow);
    }


    /**
     * <h2>Configures the world for rendering.</h2>
     * Configures the {@link GameLevel#camera}, {@link GameLevel#viewport}, and {@link GameLevel#tiledMapRenderer}
     * in order to render the TiledMap.
     */
    private void renderInit() {
        Gdx.gl.glClearColor(0.07843137255f, 0.04705882353f, 0.1098039216f, 0f); // RGB number / 255

        // Create the renderer, providing the GameScene's TileMap instance as its argument
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        // Camera configuration
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.7f; // Sets the zoom of the game camera

        // Viewport that manages the camera's width and height, in the event of a resize
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        // Set the view of the renderer to the camera's view
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera());
        tiledMapRenderer.getBatch().setProjectionMatrix(camera.combined);
    }


    /**
     * Collision initialisation
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
                if (contact.getFixtureA() == endZoneFixture || contact.getFixtureB() == endZoneFixture)
                    levelComplete();
                else {
                    stage.addActor(new QuestionWindow());

                    if (contact.getFixtureA() != player.playerFixture)
                        Gdx.app.postRunnable(() -> contact.getFixtureA().getBody().destroyFixture(contact.getFixtureA()));
                    else
                        Gdx.app.postRunnable(() -> contact.getFixtureB().getBody().destroyFixture(contact.getFixtureB()));

                    // Hides the TileMap's tile "obstacle"
                    map.getLayers().get("obstacle" + questionIndex).setVisible(false);
                }
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


    public void endZoneInit() {
        RectangleMapObject gameExit = (RectangleMapObject) map.getLayers().get("objects").getObjects().get("exit");
        Rectangle rectangle = gameExit.getRectangle();

        Body body;
        PolygonShape shape = new PolygonShape();
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

        body = world.createBody(bodyDef);
        shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
        fixtureDef.shape = shape;

        endZoneFixture = body.createFixture(fixtureDef);
    }


    /**
     * Input initialisation for user input
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
                    case Input.Keys.NUM_1: // TODO - remove once done
                        stage.addActor(new RestartLevel());
                        break;
                    case Input.Keys.ESCAPE:
                        gamePausedWindow.setVisible(!gamePausedWindow.isVisible());

                        if (gamePausedWindow.isVisible()) pause();
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

    /**
     * Restricts user input by removing the {@link #keyListener}'s from the {@link #inputHandlers}. At the same time,
     * stopping the {@link Player}'s ability to move.
     */
    public void disableUserInput() {
        inputHandlers.removeProcessor(keyListener);
        pause();
    }


    /**
     * Re-enables user input by adding the {@link #keyListener}'s to the {@link #inputHandlers}. At the same time,
     * enabling the {@link  Player}'s ability to move.
     */
    public void enableUserInput() {
        inputHandlers.addProcessor(keyListener);
        resume();
    }


    /**
     * Moves the player to {@link GameLevel#spawn}.
     */
    public void playerToSpawn() {
        spawn.getRectangle().getPosition(player.pos);
    }


    /**
     * Decreases the {@link #player}'s health upon incorrect answer input to the current question.
     *
     * @param actor The actor to be removed from the {@link #stage}, this should be the question window that
     *              is no longer required.
     */
    public static void decreasePlayerLives(Actor actor) {
        if ((healthBar.getValue() - healthBar.getStepSize()) == 0f) {
            healthBar.setValue(0f);
            stage.getActors().removeValue(actor, true);
            stage.addActor(new RestartLevel());
        } else healthBar.setValue(healthBar.getValue() - healthBar.getStepSize());

        playIncorrectAns();
    }


    /**
     * Displays the {@link LevelInfoWindow} on the stage, only if {@link EducationGame#isLevelWindowsHidden()} is true.
     */
    private void showGameInfo() {
        if (!CLIENT.isLevelWindowsHidden()) {
            stage.addActor(new LevelInfoWindow());
        }
    }


    /**
     * Determines if the game should load the next level (if exists) or return to the main menu.
     */
    public void levelComplete() {
        if (getLevelsIterated().hasNext())
            CLIENT.setScreen(new LoadingScreen());
        else
            CLIENT.setScreen(new MenuScreen());
    }

    //#endregion
    //#region rendering

    /**
     * Draws the sprite using the {@link #tiledMapRenderer}.
     */
    private void drawSprite(Sprite sprite) {
        tiledMapRenderer.getBatch().begin();
        sprite.draw(tiledMapRenderer.getBatch());
        tiledMapRenderer.getBatch().end();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen before rendering the next frame

        camera.update();
        renderWorld();
        renderDebug();

        processCollisions();
        processCameraMovement();

        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();
    }


    /**
     * Instructs the {@link OrthogonalTiledMapRenderer} to begin rendering
     */
    private void renderWorld() {
        tiledMapRenderer.setView
                (camera.combined, (camera.position.x - (camera.viewportWidth * .5f)),
                        (camera.position.y - (camera.viewportHeight * .5f)), camera.viewportWidth, camera.viewportHeight);
        tiledMapRenderer.render();

        drawSprite(player); // draw the player character
    }


    /**
     * Renders the {@link Player}
     */
    private void renderPlayer() {
//        drawSprite(player);
    }


    /**
     * Updates the Box2D world.
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
     * Determines the camera's behaviour and position.
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
        // re-calculates and updates height and width of the camera's viewport
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        // updates the UI viewport
        stage.getViewport().update(width, height);
    }


    /**
     * Pauses the {@link Player}'s movement.
     */
    @Override
    public void pause() {
        player.pauseMovement();
    }


    /**
     * Resumes the {@link Player}'s movement.
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
        stage.dispose();
    }


    //#endregion
    //#region get

    public static Stage getStage() {
        return stage;
    }

    public Vector3 getDesiredCamPos() {
        return desiredCamPos;
    }

    public static TiledMap getMap() {
        return map;
    }

    public static World getWorld() {
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