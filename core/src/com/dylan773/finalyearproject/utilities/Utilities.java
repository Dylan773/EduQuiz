package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * <h1>Utility class, providing helper methods for the entire scope of the application</h1>
 * <p>
 * These methods typically reduce the repetition and volume of code within this application.
 *
 * @author Dylan Brand
 */
public class Utilities {

    /**
     * <h2>Instantiates a new Label</h2>
     * This method can be called to instantiate a new {@link Label} without having to create a new Label keyword each time.
     * <p>
     * Instantiating a Label with: Label label = new Label(...) is unnecessary. However, there may be select cases
     * where creating a Label within a variable is the most suitable approach.
     *
     * @param text      The text to be displayed on the label.
     * @param fontStyle The font style for this label's text.
     * @return The Label.
     */
    public static Label addLabel(String text, String fontStyle) {
        return new Label(text, Assets.SKIN, fontStyle);
    }


    /**
     * <h2>Positions an Object at the centre of the screen</h2>
     * This method can be called to set the position of an object to the centre of the current display. Ideally,
     * this method would called inside an object's set position method, for the X and Y axis.
     *
     * @param length          The length of the Object.
     * @param containerLength The length of the container/screen.
     * @return An Integer, the length of the container / 2, subtracted by the length of the Object / 2.
     */
    public static float centreObject(float length, float containerLength) {
        return (containerLength / 2) - (length / 2);
    }


    /**
     * If value is greater than max, returns max. If less than mix, returns min. If neither, returns value.
     */
    public static float clamp(float min, float max, float value) {
        return value > max ? max : value < min ? min : value;
    }


    /**
     * @param a
     * @param b
     * @param f
     * @return
     */
    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }


    /**
     * <h2>Removes an {@link Actor} from its parent.</h2>
     * This method should be called when an actor is no longer required. If the {@link Actor}'s parent is NULL,
     * the Actor remains unaffected by this call.
     *
     * @see Actor#remove()
     * @param actor - The actor to be removed from its parent.
     *
     */
    public static void destroyActor(Actor actor) {
        actor.remove();
    }


    public static float debugValue = 1f;

    public static void debugMod(float f) {
        debugValue += f;
        System.out.println("Debug value is now : " + debugValue);
    }
}