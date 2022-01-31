package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.AudioController;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import static com.dylan773.finalyearproject.utilities.Utilities.addLabel;

public class QuestionWindow extends WindowBuilder {

    private boolean p = false;
    /**
     *
     */
    public QuestionWindow() {
        super(1000f, 600f);
        buildWindow();
    }



    @Override
    protected void buildWindow() {
        this.setVisible(true);

        TextField textField = new TextField("fdsdfsf", Assets.SKIN);

        this.add(addLabel("", "subtitle")).padBottom(50f).row();
        this.add(textField);
    }
}



