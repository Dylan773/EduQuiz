package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.dylan773.finalyearproject.utilities.Assets;

/**
 * class that makes thing easier, i think
 */
public class Utilities {


    /**
     * TODO - javadoc
     * default width of 400f + pad 20f + adds a new row
     *
     * accepts a table parameter, which the button will be added to the provided table
     *
     * @param name The text (String) to be displayed on the TextButton.
     * @param table The table this button is to be added too.
     * @return This TextButton.
     */
//    public static TextButton addButton1(String name, Table table) {
//        TextButton button = new TextButton(name, Assets.SKIN);
//        table.add(button);
//        return button;
//    }

    /**
     * //TODO - Javadoc (Table)
     * Heavily reduces amount of repetition and volume of code across this application.
     * Can be called to instantiate a new Label without having to create a new Label keyword each time.
     * @param text The text (String) to be displayed on the label.
     * @param fontStyle The font style for the this label's text.
     * @return This Label.
     */
        public static Label addLabel(String text, String fontStyle) {
            return new Label(text, Assets.SKIN, fontStyle);
    }
}
