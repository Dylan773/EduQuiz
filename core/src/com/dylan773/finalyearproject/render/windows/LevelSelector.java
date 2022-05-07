package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.dylan773.finalyearproject.level.LevelFactory;
import com.dylan773.finalyearproject.utilities.DelayEvent;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import java.util.ArrayList;
import java.util.Iterator;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;
import static com.dylan773.finalyearproject.utilities.Utilities.addWindowLabel;
import static com.dylan773.finalyearproject.utilities.Utilities.destroyActor;

/**
 * <h1>Level Selection Window.</h1>
 *
 * Presents the application's available levels to the user for selection.
 *
 * @author Dylan Brand
 */
public class LevelSelector extends WindowBuilder {

    private static final ArrayList<LevelFactory.Level> LEVEL_LIST = new ArrayList<>();
    private static Iterator<LevelFactory.Level> levelsIterated;

    /**
     * <h2>Constructor.</h2>
     */
    public LevelSelector() {
        super(1000f, 600f);
        initWindow();
    }


    /**
     * Content to be displayed on the window.
     */
    @Override
    protected void initWindow() {
        // Table for absolute positioning of the button that closes this window
        Table btnCloseTable = new Table();
        btnCloseTable.setFillParent(true);

        // Vertical group for level (checkbox) positioning
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.columnAlign(Align.left);
        verticalGroup.space(3f);

        // Level selection error label
        Label lblError = new Label("", SKIN, "font", Color.SALMON);
        lblError.setAlignment(Align.center);

        // Adding the info labels to the window
        addWindowLabel("Level Selector", "subtitle", this).padTop(50f).row();
        addWindowLabel("Select the topics you wish to practice.", "default", this).pad(15f, 0f, 10f, 0f).row();

        // Populating the vertical group with CheckBox's for level selection
        ArrayList<CheckBox> selectedLevels = new ArrayList<>();
        for (LevelFactory.Level l : LevelFactory.Level.values()) {
            //for every level in all levels
            CheckBox c = new CheckBox(l.name(), SKIN);
            verticalGroup.addActor(c);

            selectedLevels.add(c); // Add each CheckBox to the ArrayList
        }

        // Button for level confirmation
        TextButton textButton = new TextButton("Confirm", SKIN);
        textButton.addListener(new ChangeListener() { // TODO - change to click listener
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedLevels.forEach(it -> {
                    if (it.isChecked())
                        LEVEL_LIST.add(LevelFactory.Level.valueOf(it.getText().toString()));
                });

                if (LEVEL_LIST.size() == 0) {
                    lblError.setText("You need to select at least one level.");
                    new DelayEvent(2000, () -> lblError.setText(""));
                } else {
                    //Collections.shuffle(LEVEL_LIST); // shuffles the levels before iteration
                    levelsIterated = LEVEL_LIST.iterator(); // Iteration of the ArrayList
                    CLIENT.setScreen(LevelFactory.newLevel(levelsIterated.next()));
                }
            }
        });

        // Button for window closing
        TextButton btnClose = new TextButton("X", SKIN, "default");
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                destroyActor(LevelSelector.this);
            }
        });

        // Adding actors to the window
        add(verticalGroup).expandY().top().padTop(40f).row();
        add(textButton).row();
        add(lblError).padTop(10f);

        // window close button config
        btnCloseTable.add(btnClose);
        addActor(btnCloseTable.top().right().pad(20f));
    }


    /**
     * @return an ArrayList of {@link LevelFactory} levels.
     */
    public static ArrayList<LevelFactory.Level> getLevelList() {
        return LEVEL_LIST;
    }


    /**
     * @return {@link #LEVEL_LIST} iterated.
     */
    public static Iterator<LevelFactory.Level> getLevelsIterated() {
        return levelsIterated;
    }
}