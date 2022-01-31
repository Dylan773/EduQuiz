package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.render.screens.MenuScreen;
import com.dylan773.finalyearproject.utilities.AudioController;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;


/**
 *
 */
public class GameBar extends Window {

    //private Dialog exitDialog;

    /**
     *
     */
    public GameBar() {
        super("", SKIN, "noBG");
        buildWindow();
    }

    /**
     *
     */
    protected void buildWindow() {
        setVisible(false);
        setResizable(false);
        setMovable(false);
        setSize(Gdx.graphics.getWidth(), 140f);
        align(0);


        HorizontalGroup group = new HorizontalGroup();
        group.space(150f); // Space between elements.
        group.center(); // Centre elements.

        //debug();


        //initExitDialog();

        // Help button
        TextButton btnHelp = new TextButton("Help", SKIN);
        btnHelp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
            }
        });

        TextButton btnOptions = new TextButton("Options", SKIN);
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                GameLevel.setOptionWindowVisibility(true);
            }
        });

        // Exit level button
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

    private void initExitDialog() {
    }


    /**
     * <h2>Private inner class for the game exit dialog.</h2>
     */
    private static class ExitDialog extends Dialog { // TODO - this might be shit

        /**
         *
         */
        public ExitDialog() {
            super("", SKIN, "round");
            setMovable(false);
            setResizable(false);
            getBackground().setMinWidth(370f);


            // Dialog config
            Label label = new Label("Are you sure?", SKIN, "subtitle");
            text(label);

            button("Yes", true);
            button("No", false);
        }

        @Override
        protected void result(Object object) {
            if (object.equals(true))
                CLIENT.setScreen(new MenuScreen());
            else
                hide();
        }
    }
}