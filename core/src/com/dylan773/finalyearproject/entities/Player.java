package com.dylan773.finalyearproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dylan773.finalyearproject.utilities.Assets;

public class Player extends Sprite {

    //**************
    // FIELDS
    //**************

    /**
     * The x and y position for this sprite (player).
     */
    private float
            posX,
            posY;

    /**
     * The movement speed for this player, 100 pixels/second.
     */
    private final float SPEED = 100;


    //**************
    // CONSTRUCTOR
    //**************

    /**
     * <h2>Player class constructor</h2>
     */
    public Player(float x, float y) { //delete both floats
        super(Assets.PLAYER_SPRITE);
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

//    public Player(float x, float y, TiledMapTileLayer wallCollisionLayer) { //delete both floats
//        super(Assets.PLAYER_SPRITE);
//        this.collisionlayer = wallCollisionLayer;
//
//        setSize(50f, 50f); // Default size of player - CHANGE BACK TO 70
//
//        posX = x; // Assigns this player's posX value to the x parameter value
//        posY = y; // Assigns this player's posY value to the y parameter value
//    }

    // **************
    // METHODS
    // **************

    /**
     * <h2>Draws the player sprite</h2>
     * Accepts a batch, to draw a Texture and calls the {@link #update(float, float)} method that is responsible for player movement controls.
     * <p>
     * @param batch - Draws a 2D rectangle that references a Texture.
     * @see Assets#PLAYER_SPRITE
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
        // ====
//        float oldX = getPosX(), oldY = getPosY();
//        boolean collisionX = false, collisonY = false;
        // ====

        setPosition(x, y); // Sets the player position

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
            //setX(posX += Gdx.graphics.getDeltaTime() * playerSpeed);
        }

        // if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        //            posY += Gdx.graphics.getDeltaTime() * SPEED;
        //        }
        //
        //        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        //            posY -= Gdx.graphics.getDeltaTime() * SPEED;
        //        }
        //
        //        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        //            posX -= Gdx.graphics.getDeltaTime() * SPEED;
        //        }
        //
        //        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        //            posX += Gdx.graphics.getDeltaTime() * SPEED;
        //            //setX(posX += Gdx.graphics.getDeltaTime() * playerSpeed);
        //        }
    }


    /**
     * @param x
     */
    public void setPlayerX(float x) {
        setX(x);
    }

    /**
     * @param y
     */
    public void setPlayerY(float y) {
        posY = y;
    }

    /**
     * @return
     */
    public float getPosX() {
        return posX;
    }

    /**
     * @return
     */
    public float getPosY() {
        return posY;
    }

}
