package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.render.screens.MenuScreen;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;
import static com.dylan773.finalyearproject.utilities.Utilities.addLabel;

/**
 * <h1>In-game option bar</h1>
 * A transparent {@link Window} consisting of three buttons placed horizontally, enabling the user to manipulate application
 * behaviour during the game state.
 * <p>
 * The GameBar does not extend the {@link com.dylan773.finalyearproject.utilities.WindowBuilder} class as its behaviour
 * is somewhat different than most other windows in this application.
 *
 * @author Dylan Brand
 */
public class GameBar extends Window {

    /** <h2>Constructor.</h2> */
    public GameBar() {
        super("", SKIN, "noBG");
        initWindow();
    }


    /**
     * Content for the GameBar window.
     */
    protected void initWindow() {
        setVisible(false);
        setResizable(false);
        setMovable(false);
        setSize(Gdx.graphics.getWidth(), 200f);

        // Horizontal group for button placement.
        HorizontalGroup group = new HorizontalGroup();
        group.space(150f); // Space between elements.

        // Button for game help information
        TextButton btnHelp = new TextButton("Help", SKIN);
        btnHelp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                GameLevel.getStage().addActor(new GameControls());
            }
        });

        // Button for game option controls
        TextButton btnOptions = new TextButton("Options", SKIN);
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                GameLevel.getStage().addActor(new OptionsWindow());
            }
        });

        // Button for level exit control
        TextButton btnExit = new TextButton("Exit", SKIN);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                new ExitDialog().show(GameLevel.getStage());
            }
        });

        // Adds each actor to the horizontal group, then add the group to the window.
        group.addActor(btnHelp);
        group.addActor(btnOptions);
        group.addActor(btnExit);

        add(group);
    }


    /**
     * <h2>Private inner class for the level exit dialog.</h2>
     * Prompts the user with a dialog, confirming if they want to exit the current game session.
     */
    private static class ExitDialog extends Dialog {

        /**
         * A round dialog window, displaying two buttons with a YES or NO option.
         * <p>
         * Yes - Returns the user to the application main menu. <br>
         * No - Returns the user to the current game session.
         */
        public ExitDialog() {
            super("", SKIN, "round");
            setMovable(false);
            setResizable(false);
            getBackground().setMinWidth(370f);

            // Text to be displayed on the dialog.
            text(addLabel("Are you sure?", "subtitle"));

            // Dialog buttons
            button("Yes", true);
            button("No", false);
        }

        @Override
        protected void result(Object object) {
            if (object.equals(true))
                CLIENT.setScreen(new MenuScreen());
            else hide();
        }
    }
}