package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import static com.dylan773.finalyearproject.utilities.Utilities.centreObject;

/**
 * Abstract class that
 */
public abstract class WindowBuilder extends Window {

    /**
     *
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public WindowBuilder(float width, float height) {
        super("", Assets.SKIN, "round");
        //this.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.OPTIONS_BACKGROUND)));
        setResizable(false);
        setMovable(false);
        setSize(width, height);
        setPosition(centreObject(getWidth(), Gdx.graphics.getWidth()), centreObject(getHeight(), Gdx.graphics.getHeight()));
        setVisible(false); // By default, this window is initially hidden
        padTop(0f);
        //debug();

        // TODO - title table
    }

    /**
     * Abstract method that can be overridden by extending (child) classes to construct the respective window.
     */
    protected abstract void initWindow();
}
