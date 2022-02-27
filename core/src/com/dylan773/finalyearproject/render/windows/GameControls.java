package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;
import static com.dylan773.finalyearproject.utilities.Utilities.destroyActor;

/**
 * <h1>Window that shows basic level information and controls on the {@link GameBar}</h1>
 */
public class GameControls extends WindowBuilder {

    /** <h2>Constructor</h2> */
    public GameControls() {
        super(1000f, 600f); // TODO - change to screensize percentage?
        initWindow();
    }

    /** Content to be displayed on the window. */
    @Override
    protected void initWindow() {
        setVisible(true);
        pad(20f, 0f, 20f, 0f);

        // Game Objective and controls subtitles
        Label lblObjectiveTitle = new Label("Objective", SKIN, "subtitle");
        Label lblControlsTitle = new Label("Controls", SKIN, "subtitle");

        // Main content label
        Label lblObjectiveText = new Label("", SKIN);
        lblObjectiveText.setAlignment(Align.center);
        lblObjectiveText.setText("Your task is simple. " +
                "Navigate the map, exploring new areas, and interests.\n" +
                "BUT that wont be easy, certain areas require a correct answer for entry.\n" +
                "Correctly answer each question to unlock new areas. ");

        // Key control label
        Label lblKeyControls = new Label("", SKIN);
        lblKeyControls.setText("W - UP\nA - LEFT\nS - DOWN\nD - RIGHT");

        // Window close button
        TextButton btnClose = new TextButton("Close", SKIN); // TODO - change to click listener
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
//                setVisible(false);
                destroyActor(GameControls.this);
            }
        });

        // Adding the actors to the window.
        add(lblObjectiveTitle).row();
        add(lblObjectiveText).row();
        add(lblControlsTitle).padTop(30f).row();
        add(lblKeyControls).row();
        add(btnClose).expandY().bottom();
    }
}
