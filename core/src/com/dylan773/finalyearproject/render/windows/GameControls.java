package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;
import static com.dylan773.finalyearproject.utilities.Utilities.addWindowLabel;
import static com.dylan773.finalyearproject.utilities.Utilities.destroyActor;

/**
 * <h1>Window that shows basic level information and controls.</h1>
 */
public class GameControls extends WindowBuilder {

    private Table table;

    /** <h2>Constructor</h2> */
    public GameControls() {
        super(650f, 600f); // TODO - change to screensize percentage?
        initWindow();
    }


    /** Content to be displayed on the window. */
    @Override
    protected void initWindow() {
        setVisible(true);
        pad(20f, 0f, 20f, 0f);

        table = new Table();
        table.setFillParent(true);

        // Window close button
        TextButton btnClose = new TextButton("Close", SKIN); // TODO - change to click listener
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                destroyActor(GameControls.this);
            }
        });

        addWindowLabel("Player", "subtitle", this).pad(15f, 0f, 5f, 0f).row();
        addWindowLabel(
                "W: Player Up" +
                "\nA: Player Left" +
                "\nS: Player Down" +
                "\nD: Player Right"
                , "default", this).padBottom(5f).row();
        addWindowLabel("Arrow Keys: Player Direction", "default", this).padBottom(25f).row();
        addWindowLabel("Miscellaneous", "subtitle", this).padBottom(5f).row();
        addWindowLabel("ESC: Menu Toggle", "default", this).row();
        addWindowLabel("Cursor: Interaction with UI", "default", this).row();
        add(btnClose).expandY().bottom();
    }
}
