package com.dylan773.finalyearproject.level;


public class LevelFactory {

    public enum Level {
        Museum
    }

    /**
     *
     */
    private static String[] LevelPaths = {
            "levels/museum/museum.tmx"
    };

    public static GameLevel newLevel(Level l) {
        return new GameLevel(LevelPaths[l.ordinal()]);
    }
}
