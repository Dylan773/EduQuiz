package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
//        setSize(Gdx.graphics.getWidth()* 0.5f, Gdx.graphics.getHeight()* 0.5f); // TODO - psotion window based on user screen size
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


    /**
     * <h2>Instantiates a new Label</h2>
     * Heavily reduces repetition and volume of code across this application.
     * Can be called to instantiate a new Label to a Table without having to create a new Label keyword each time.
     * <p></p>
     * <p>
     * Instantiating a Label with; Label label = new Label(...) is not necessary.
     *
     * @param text      The text (String) to be displayed on the label.
     * @param fontStyle The font style for the this label's text.
     * @return This Label.
     */
    public Cell<Label> addLabel(String text, String fontStyle) {
        return add(new Label(text, Assets.SKIN, fontStyle));
    }
}
