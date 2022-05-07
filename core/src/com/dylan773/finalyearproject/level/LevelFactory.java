package com.dylan773.finalyearproject.level;

/**
 * <h1>This application's level handler.</h1>
 */
public class LevelFactory {

    // TODO - write notes on how this works
    public enum Level {
        History,
        Maths
    }

    /**
     * The file path (asset pointer) of each application level.
     */
    private static final String[] LevelPaths = {
            "levels/museum.tmx",
            "levels/school.tmx"
    };


    /**
     * Instantiates a new {@link GameLevel}
     *
     * @param l The Level to be instantiated.
     * @return The {@link GameLevel} to be created.
     */
    public static GameLevel newLevel(Level l) {
        return new GameLevel(LevelPaths[l.ordinal()], l);
    }
}
