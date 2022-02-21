package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;

//TODO - commit in gamebar branch
// add labels - use the addLabel method
// rectify (do research) about label text and centering - its currently shit/dont like it
// a button to close the window is repeated a lot, create a method in util that multiple windows can use?

/**
 *
 */
public class GameControls extends WindowBuilder {

    /**
     *
     */
    public GameControls() {
        super(1000f, 600f); // TODO - change to screensize percentage?
        initWindow();
    }

    @Override
    protected void initWindow() {
        setVisible(true);
        pad(20f, 0f, 20f, 0f);

        // Textarea - disable input/ make it read-only + max-width?

        // TODO - create an add label method! (one in utilities?)
        // Game Objective and controls  sub-titles
        Label lblObjectiveTitle = new Label("Objective", SKIN, "subtitle");
        Label lblControlsTitle = new Label("Controls", SKIN, "subtitle");

        // TODO - text goes off screen if too long
        Label lblObjectiveText = new Label("", SKIN);
        lblObjectiveText.setAlignment(Align.center);
        lblObjectiveText.setText("Your task is simple. " +
                "Navigate the map, exploring new areas, and interests.\n" +
                "BUT that wont be easy, certain areas require a correct answer for entry.\n" +
                "Correctly answer each question to unlock new areas. ");



        Label lblKeyControls = new Label("", SKIN);
        lblKeyControls.setText("W - UP\nA - LEFT\nS - DOWN\nD - RIGHT");


        // Window close button
        TextButton btnClose = new TextButton("Close", SKIN); // TODO - this is duplicated A LOT!
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                setVisible(false);
            }
        });

        add(lblObjectiveTitle).row();
        add(lblObjectiveText).row();

        add(lblControlsTitle).padTop(30f).row();

        add(lblKeyControls).row();
        add(btnClose).expandY().bottom();
        //debug();
    }
}
