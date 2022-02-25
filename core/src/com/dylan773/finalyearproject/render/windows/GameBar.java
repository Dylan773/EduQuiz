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

/**
 * <h1>In-game option bar</h1>
 * A transparent window consisting of three buttons, enabling the user to manipulate application behaviour
 * during the game state.
 *
 * @author Dylan Brand
 */
public class GameBar extends Window {

    /**
     * <h2>GameBar Constructor</h2>
     * Creates this application's in-game toolbar, calling it's super() and {@link #initWindow()}.
     */
    public GameBar() {
        super("", SKIN, "noBG");
        initWindow();
    }


    /**
     * Constructs the GameBar window.
     */
    protected void initWindow() {
        setVisible(false);
        setResizable(false);
        setMovable(false);
        setSize(Gdx.graphics.getWidth(), 140f);
        align(0);

        // Horizontal group for button placement.
        HorizontalGroup group = new HorizontalGroup();
        group.space(150f); // Space between elements.
        group.center(); // Centre elements.

        // Button for game help information
        TextButton btnHelp = new TextButton("Help", SKIN);
        btnHelp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                GameLevel.getStage().addActor(new GameControls()); // TODO - does this add multiple actors of the same type everytime its clicked?
            }
        });

        // Button for game option controls
        TextButton btnOptions = new TextButton("Options", SKIN);
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                //GameLevel.setOptionWindowVisibility(true);
                GameLevel.getStage().addActor(new OptionsWindow()); //TODO - change this (might add multiple windows to the stage).
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
     * <h2>Private inner class for the game exit dialog.</h2>
     * Prompts the user with a dialog, confirming if they want to exit the current game session.
     */
    private static class ExitDialog extends Dialog { // TODO - this might be crap

        /**
         * A round dialog window, displaying two buttons with a YES or NO option. <p>
         * <p>
         * Yes - Returns the user to the application main menu. <br>
         * No - Returns the user to the current game session.
         */
        public ExitDialog() {
            super("", SKIN, "round");
            setMovable(false);
            setResizable(false);
            getBackground().setMinWidth(370f);

            // Dialog config
            Label label = new Label("Are you sure?", SKIN, "subtitle");
            text(label);

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