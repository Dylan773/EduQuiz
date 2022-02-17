package com.dylan773.finalyearproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.Utilities;

/**
 * <h1>This application's controllable player</h1>
 */
public class Player extends Sprite {
    public World world; // The Box2D world.
    public Body body; // The Box2D body.
    public GameLevel level;
    /*
     * Fields
     */
    /**
     * The x and y position for this sprite (player).
     */
    public Vector2 pos = new Vector2(450f, 350f);

    /**
     * The movement speed for this player, 100 pixels/second.
     */
    public float speed = 100f;

    public Fixture playerFixture;

    /*
     * Constructor
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
     *
     */
    public void definePlayer() { body = createBox2DPlayerBody(world, getWidth(), getWidth()); }

    /**
     *
     * @param w
     * @param width
     * @param height
     * @return
     */
    public Body createBox2DPlayerBody(World w, Float width, Float height) {
        // Create the main body
        BodyDef bdef = new BodyDef();
        bdef.position.set(pos);
        bdef.type = BodyDef.BodyType.DynamicBody;

        Body body = w.createBody(bdef);

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
     * Accepts a batch, to draw a Texture and calls the {@link #update(float, float)} method that is responsible for player movement controls.
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
        body.setTransform(pos.x + (getWidth()/ 2), pos.y  + (getHeight()/2), 0);
    }

    private float speed() {
        return (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) ? speed * Utilities.debugValue : speed;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean boundCheck(int x, int y) {
        return ((TiledMapTileLayer) level.getMap().getLayers().get("Floor")).getCell(Math.round((pos.x + (getWidth() * .5f)) + x) / 16, Math.round(pos.y + y) / 16) != null;
    }


    //TODO - change collision layer from terrain?
    private boolean abstractBoundCheck(int x, int y, TiledMap tiledMap) {
        return ((TiledMapTileLayer) tiledMap.getLayers().get("Terrain")).getCell(Math.round((pos.x + (getWidth() * .5f)) + x) / 16, Math.round(pos.y + y) / 16) != null;
    }

    /**
     * <h2> Stops the player's ability to move.</h2>
     * Should only be called when the game session is in a paused state.
     * // TODO - stop the controls not his feet
     */
    public void pauseMovement() { speed = 0f; }

    /**
     * <h2>Enables the player's ability to move, at the default speed.</h2>
     * Should be called when the game sessions has left the paused state.
     */
    public void resumeMovement() { speed = 100f; }
}
