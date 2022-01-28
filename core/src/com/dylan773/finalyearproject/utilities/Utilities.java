package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.scenes.scene2d.ui.Label;


//TODO - Javadoc for class header and methods

/**
 * class that makes thing easier, i think
 */
public class Utilities {


    /**
     * javadoc...
     *
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
     * <h2>Instantiates a new Label</h2>
     * Heavily reduces repetition and volume of code across this application.
     * Can be called to instantiate a new Label to a Table without having to create a new Label keyword each time.
     * <p></p>
     * <p>
     * Instantiating a Label with; Label label = new Label(...) is not necessary.
     *
     * @param text      The text (String) to be displayed on the label.
     * @param fontStyle The font style for the this label's text.
     * @return This Label.
     */
    public static Label addLabel(String text, String fontStyle) {
        return new Label(text, Assets.SKIN, fontStyle); // Sets the Label's skin to this applications default skin.
    }

    //TODO - tweak?

    /**
     * @param length
     * @param containerLength
     * @return
     */
    public static float centreObject(float length, float containerLength) {
        return (containerLength / 2) - (length / 2);
    }
}
