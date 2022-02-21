package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.scenes.scene2d.ui.Label;


//TODO - Javadoc for class header and methods

/**
 * class that makes thing easier, i think
 */
public class Utilities {


    /**
     * <h2>Instantiates a new Label</h2>
     * Heavily reduces repetition and volume of code across this application.
     * Can be called to instantiate a new Label to a Table without having to create a new Label keyword each time.
     * <br>
     * Instantiating a Label with: Label label = new Label(...) is not necessary.
     *
     * @param text      The text to be displayed on the label.
     * @param fontStyle The font style for the this label's text.
     * @return This Label.
     */
    public static Label addLabel(String text, String fontStyle) {
        return new Label(text, Assets.SKIN, fontStyle);
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

    /**
     * If value is greater than max, returns max. If less than mix, returns min. If neither, returns value.
     */
    public static float clamp(float min, float max, float value) {
        return  value > max ? max : value < min ? min : value;
    }

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }



    public static float debugValue = 1f;
    public static void debugMod(float f) {
        debugValue += f;
        System.out.println("Debug value is now : " + debugValue);
    }
}
