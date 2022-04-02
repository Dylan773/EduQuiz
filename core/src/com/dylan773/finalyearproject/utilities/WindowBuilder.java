package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.render.windows.LevelSelector;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;
import static com.dylan773.finalyearproject.utilities.Utilities.centreObject;
import static com.dylan773.finalyearproject.utilities.Utilities.destroyActor;

/**
 * <h2>Abstract class that can be extended to simplify the construction of game windows.</h2>
 *
 * @author Dylan Brand
 */
public abstract class WindowBuilder extends Window {

    Table btnCloseTable = new Table();


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
        super("", SKIN, "round");
//        setVisible(false); // By default, this window is initially hidden
        setResizable(false);
        setMovable(false);
        setSize(width, height);
        centreStage();

        padTop(0f); // why?


        btnCloseTable.setFillParent(true);

        TextButton btnClose = new TextButton("X", SKIN, "default");
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                destroyActor(WindowBuilder.this);


            }
        });

        // window close button config
        btnCloseTable.add(btnClose);
        addActor(btnCloseTable.top().right().pad(25f));
    }

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

    
    /**
     * <h2>Instantiates a new Label</h2>
     * Heavily reduces repetition and volume of code across this application.
     * Can be called to instantiate a new Label to a Table without having to call a new Label keyword each time.
     * <p>
     * Instantiating a Label with Label label = new Label(...) is not necessary.
     *
     * @param text      The text to be displayed on the label.
     * @param fontStyle The font style for this label's text.
     * @return A cell, with the created Label inside.
     */
    public Cell<Label> addWindowLabel(String text, String fontStyle) {
        return add(new Label(text, SKIN, fontStyle));
    }


    public static <T extends Cell> T expandfill(T actor) {
        actor.fill().expand();
        return actor;
    }

}
