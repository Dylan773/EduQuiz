package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dylan773.finalyearproject.level.LevelFactory;
import com.dylan773.finalyearproject.utilities.WindowBuilder;
import javafx.geometry.Pos;

import javax.swing.*;
import java.util.ArrayList;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playButtonSound;

public class LevelSelector extends WindowBuilder {
    private static final ArrayList<LevelFactory.Level> LEVEL_LIST = new ArrayList<>();

    public LevelSelector() {
        super(1000f, 600f);
        buildWindow();
    }


    @Override
    protected void buildWindow() {
        setVisible(false);

        Label lblTitle = new Label("Level Selector", SKIN, "subtitle");
        add(lblTitle).padBottom(20f).padTop(20f).center();

        TextButton btnClose = new TextButton("X", SKIN, "arcade");
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playButtonSound();
                setVisible(false);
            }
        });
        add(btnClose).row();

        //TODO - play correct audio dynamically

        ArrayList<CheckBox> selectedLevels = new ArrayList();
        setVisible(true); // not visible until created on button click

        //for every level in all levels
        for (LevelFactory.Level l : LevelFactory.Level.values()) {
            CheckBox c = new CheckBox(l.name(), SKIN);
            add(c).row();

            selectedLevels.add(c); // Add each CheckBox to the ArrayList
        }

        // red label
        Label lblError = new Label("", SKIN, "font", Color.SALMON);
        lblError.setAlignment(Align.center);

        TextButton textButton = new TextButton("Confirm", SKIN);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedLevels.forEach(it -> {
                    if (it.isChecked())
                        LEVEL_LIST.add(LevelFactory.Level.valueOf(it.getText().toString()));
                });

                if (LEVEL_LIST.size() == 0)
                    lblError.setText("Select at least one level");
                else
                    CLIENT.setScreen(LevelFactory.newLevel(LEVEL_LIST.get(0)));
                playButtonSound();
            }
        });


        //padTop(20f);

        add(textButton).expandY().bottom().row();
        debug();
        add(lblError).padTop(10f);
    }


    /**
     * @return
     */
    public static ArrayList<LevelFactory.Level> getLevelList() {
        return LEVEL_LIST;
    }


//        if (optionsWindow.isVisible()) {
//        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
//            optionsWindow.setVisible(false);
//    }
}
