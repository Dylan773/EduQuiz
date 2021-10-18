package com.dylan773.finalyearproject;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.awt.event.InputEvent;
import java.util.function.Consumer;

public class Utilities extends Window {

    public Utilities(String title, Skin skin) {
        super(title, skin);
    }

//    public static void addButton(String text, Consumer<InputEvent> e) {
//        TextButton button = new TextButton(text, Assets.SKIN);
//        add(button).row();
//        // audio click sound
//    }
}
