package com.dylan773.finalyearproject.level;

/**
 *
 */
public class LevelFactory {

    public enum Level {
        History,
        Maths
    }

    /**
     *
     */
    private static String[] LevelPaths = {
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
