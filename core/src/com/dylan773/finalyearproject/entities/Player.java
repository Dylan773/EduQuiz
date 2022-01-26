package com.dylan773.finalyearproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.dylan773.finalyearproject.utilities.Assets;

public class Player extends Sprite {

    /*
     * FIELDS
     */

    /**
     * The x and y position for this sprite (player).
     */
    private float posX, posY;

    /**
     * The movement speed for this player, 100 pixels/second.
     */
    private final float SPEED = 100;


    //*****************************************
    private TiledMapTileLayer collisionLayer;
    private String collisionKey = "blocked";


    //TODO - remove the setting of the player position from the constructor
    /*
     * CONSTRUCTOR
     */

    /**
     * <h2>Player class constructor</h2>
     */
    public Player(float x, float y) { //delete both floats
        super(Assets.KNIGHT_SPRITE);
        setSize(50f, 50f); // Default size of player - CHANGE BACK TO 70

        posX = x; // Assigns this player's posX value to the x parameter value
        posY = y; // Assigns this player's posY value to the y parameter value
    }

    /**
     * <h2>Player class constructor</h2>
     */
    public Player(float x, float y, Texture texture) { //delete both floats
        super(texture);
        setSize(50f, 50f); // Default size of player - CHANGE BACK TO 70

        posX = x; // Assigns this player's posX value to the x parameter value
        posY = y; // Assigns this player's posY value to the y parameter value
    }

    public Player(TiledMapTileLayer collisionLayer) {
        super(Assets.KNIGHT_SPRITE);
        this.collisionLayer = collisionLayer;
    }

    // METHODS

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
        update(posX, posY); // Uses the posX/posY values to set the player position when drawn
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
        setPosition(x, y); // Sets the player position

        boolean collideX = false,
                collideY = false;

        float
                oldX = getX(),
                oldY = getY();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            posY += Gdx.graphics.getDeltaTime() * SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            posY -= Gdx.graphics.getDeltaTime() * SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            posX -= Gdx.graphics.getDeltaTime() * SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            posX += Gdx.graphics.getDeltaTime() * SPEED;
        }
    }

    /**
     * write javadoc
     *
     * @param x
     * @param y
     */
//    public boolean isCellBlocked(float x, float y) {
//        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()),
//                (int) y / collisionLayer.getHeight());
//
//        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(collisionKey);
//    }

//    public boolean collisionRight() {
//        for (float inc = 0; inc <= getHeight(); inc += collisionLayer.getTileHeight() / 2)
//            if (isCellBlocked(getX(), getWidth() + inc))
//                return true;
//        return false;
//    }
}
