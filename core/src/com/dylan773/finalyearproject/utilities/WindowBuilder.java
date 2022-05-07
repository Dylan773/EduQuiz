package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.Utilities.centreObject;

/**
 * <h2>Abstract class that can be extended to simplify the construction of game windows.</h2>
 *
 * @author Dylan Brand
 */

public abstract class WindowBuilder extends Window {

    /**
     * WindowBuilder constructor that determines the basic functionality and behaviour of ALL windows that extend
     * this class.
     *
     * @param width  The width of the window.
     * @param height The height of the window.
     */
    public WindowBuilder(float width, float height) {
        super("", SKIN, "round");
        setResizable(false);
        setMovable(false);
        setSize(width, height);
        centreStage();

        padTop(0f);
    }


    /**
     * Positions the window in the centre of the focused screen's stage.
     */
    private void centreStage() {
        setPosition(centreObject(getWidth(), Gdx.graphics.getWidth()), centreObject(getHeight(), Gdx.graphics.getHeight()));
    }


    @Override
    public void pack() {
        super.pack();
        centreStage();
    }


    @Override
    public float getPrefWidth() {
        return Math.max(super.getPrefWidth(), 1000f);
    }


    @Override
    public float getPrefHeight() {
        return Math.max(super.getPrefHeight(), 600f);

    }


    /**
     * Abstract method that MUST be overridden by child classes to construct the respective window.
     */
    protected abstract void initWindow();
}