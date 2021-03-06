package com.dylan773.finalyearproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.Utilities;

/**
 * <h1>This application's controllable player</h1>
 *
 * @author Dylan Brand
 */
public class Player extends Sprite {
    public World world; // The Box2D world.
    public Body body; // The Box2D body.
    public GameLevel level;

    /**
     * The x and y position for this sprite (player).
     */
    public Vector2 pos = new Vector2(450f, 350f);

    /**
     * The movement speed for this player, 100 pixels/second.
     */
    private static float speed = 100f;

    /**
     * The player's Box2D fixture.
     */
    public Fixture playerFixture;


    /**
     * <h2>Player Constructor</h2>
     *
     * @param level The current {@link GameLevel}.
     */
    public Player(GameLevel level) {
        super(Assets.KNIGHT_SPRITE);

        // Sprite properties
        setSize(40f, 40f); // Default size of player - CHANGE BACK TO 70
        setOrigin(getWidth() / 2, getHeight() / 2);

        this.world = level.getWorld();
        this.level = level;
        definePlayer();
    }


    /**
     * Defines the player's Box2D body.
     */
    public void definePlayer() {
        body = createBox2DPlayerBody(world);
    }


    /**
     * Constructs the player's Box2D body for collision detection.
     *
     * @param w The Box2D world the player object will reside in.
     * @return The Box2D body of the player.
     */
    public Body createBox2DPlayerBody(World w) {
        // Create the main body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Body body = w.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();

        // Box around the player
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth() / 2, getHeight() / 2);

        fixtureDef.shape = polygonShape;
        fixtureDef.density = 10;
        playerFixture = body.createFixture(fixtureDef);

        return body;
    }


    /**
     * <h2>Draws the player sprite</h2>
     * Accepts a batch, to draw a Texture and calls the {@link #update} method that is responsible for player movement controls.
     * <p>
     *
     * @param batch - Draws a 2D rectangle that references a Texture.
     * @see Assets#KNIGHT_SPRITE
     */
    @Override
    public void draw(Batch batch) {
        update(pos.x, pos.y); // Uses the posX/posY values to set the player position when drawn
        super.draw(batch);
    }

    //TODO - Replace with switch statement?

    /**
     * <h2>Player movement</h2>
     * Checks for keyboard input and changes the player's position on the map on key press.
     * <p>
     * Key W - Moves the player (sprite) upwards.<br>
     * Key S - Moves the player (sprite) downwards.<br>
     * Key A - Moves the player (sprite) left.<br>
     * Key D - Moves the player (sprite) right.
     *
     * @see com.badlogic.gdx.Input.Keys
     */
    public void update(float x, float y) {

        if (Gdx.input.isKeyPressed(Input.Keys.W) && boundCheck(0, 1)) {
            pos.y += Gdx.graphics.getDeltaTime() * speed();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && boundCheck(0, -1)) {
            pos.y -= Gdx.graphics.getDeltaTime() * speed();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && boundCheck(-1, 0)) {
            pos.x -= Gdx.graphics.getDeltaTime() * speed();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && boundCheck(1, 0)) {
            pos.x += Gdx.graphics.getDeltaTime() * speed();
        }

        setPosition(x, y); // Sets the player position
        body.setTransform(pos.x + (getWidth() / 2), pos.y + (getHeight() / 2), 0);
    }


    /**
     * @return
     */
    private float speed() {
        return (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) ? speed * Utilities.debugValue : speed;
    }


    /**
     * <h2>Collision detection</h2>
     * <p>
     * Checks the corresponding {@link #level}'s TiledMap properties for the specified ("Floor") tile layer.
     * <p>
     * If the player's desired position (+/- 1 of their current X or Y co-ordinate), does not intersect with the
     * specified layer, a false value will be returned. Thus, rejecting the position change.
     *
     * @param x The player's desired X position, +/- 1 of their current X co-ordinate.
     * @param y The player's desired Y position, +/- 1 of their current Y co-ordinate.
     * @return Boolean result, indicating whether the desired position change will be intersecting with the "Floor"
     * tile layer.
     */
    private boolean boundCheck(int x, int y) {
        return ((TiledMapTileLayer) level.getMap().getLayers().get("Floor")).getCell(Math.round((pos.x + (getWidth() * .5f)) + x) / 16, Math.round(pos.y + y) / 16) != null;
    }


    /**
     * Set's the player's speed to 0, stopping the player's ability to move.
     * Should only be called when the game session is in a paused state.
     */
    public static void pauseMovement() {
        speed = 0f;
    }


    /**
     * <h2>Enables the player's ability to move, at the default speed.</h2>
     * Should be called when the game session has left the paused state.
     */
    public static void resumeMovement() {
        speed = 100f;
    }


}
