package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.render.screens.MenuScreen;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;
import static com.dylan773.finalyearproject.utilities.Utilities.addLabel;
import static com.dylan773.finalyearproject.utilities.Utilities.centreObject;

public class GamePausedWindow extends Window {

    public GamePausedWindow() {
        super("", SKIN, "round");
        initWindow();

        debug();
    }

    // TODO - player can still move when visible
    // TODO - create a and button method that accepts a string and click listener and call that method

    private void initWindow() {
        setVisible(false);
        setResizable(false);
        setMovable(false);
        setSize(600f, 800f);




        pad(25f, 50f, 25f, 50f);


//        TextButton btnExit = new TextButton("Exit", SKIN);
//        btnExit.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                playButtonSound();
//                new ExitDialog().show(GameLevel.getStage());
//            }
//        });

//        VerticalGroup buttonGroup = new VerticalGroup();
//        buttonGroup.space(15f);
//
//        buttonGroup.addActor(new TextButton("Options", SKIN));
//        buttonGroup.addActor(new TextButton("Controls", SKIN));
//        buttonGroup.addActor(btnExit);

        add(new Label("Paused", SKIN, "subtitle")).padBottom(50f).row();
//        add(buttonGroup).expandY().row();

        newButton("Options").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new ExitDialog().show(GameLevel.getStage());
            }
        });

        newButton("Controls").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new ExitDialog().show(GameLevel.getStage());
            }
        });

        newButton("Exit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new ExitDialog().show(GameLevel.getStage());
            }
        });

        add(new Label("ESC to resume.", SKIN, "font", Color.SALMON)).padTop(15f);

        pack();
        setPosition(centreObject(getWidth(), Gdx.graphics.getWidth()), centreObject(getHeight(), Gdx.graphics.getHeight()));
    }

    // TODO - dont like this, too many similar methods in different classes - create one single method
    public TextButton newButton(String text) {
        TextButton textButton = new TextButton(text, SKIN);

        add(textButton).width(350f).padBottom(20f).row();

        return textButton;
    }



    /**
     * <h2>Private inner class for the level exit dialog.</h2>
     * Prompts the user with a dialog, confirming if they want to exit the current game session.
     */
    public static class ExitDialog extends Dialog {

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
            getBackground().setMinWidth(450f);

            // Text to be displayed on the dialog.
            text(addLabel("Are you sure?", "subtitle", Color.WHITE)).row();
            add(addLabel("progress will not be saved", "font", Color.CORAL)).padTop(10f);

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

