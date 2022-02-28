package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

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
     * <p>
     * Extending classes should inherit the behaviour and functionality, by calling super() in their respective constructor.
     *
     * @param width  The width of the window.
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
    }


    /**
     * Abstract method that MUST be overridden by child classes to construct the respective window.
     */
    protected abstract void initWindow();


    /**
     * <h2>Instantiates a new Label</h2>
     * Heavily reduces repetition and volume of code across this application.
     * Can be called to instantiate a new Label to a Table without having to create a new Label keyword each time.
     * <p>
     * Instantiating a Label with; Label label = new Label(...) is not necessary.
     *
     * @param text      The text to be displayed on the label.
     * @param fontStyle The font style for this label's text.
     * @return A cell, with the created Label inside.
     */
    public Cell<Label> addWindowLabel(String text, String fontStyle) {
        return add(new Label(text, Assets.SKIN, fontStyle));
    }
}
