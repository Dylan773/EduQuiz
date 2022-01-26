package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Abstract class that
 */
public abstract class WindowBuilder extends Window {

    public WindowBuilder(float width, float height) {
        super("", Assets.SKIN);
        //this.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.OPTIONS_BACKGROUND)));
        this.setResizable(false);
        this.setMovable(false);
        this.setSize(width, height);
        this.top();
        //this.setVisible(false); // By default, this window is initially hidden TODO - mess around with this
    }

    /**
     * Abstract method that can be overridden by extending (child) classes to construct the respective window.
     */
    protected abstract void buildWindow();
}
