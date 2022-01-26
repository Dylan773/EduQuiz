package com.dylan773.finalyearproject.render.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.render.screens.GameScene;
import com.dylan773.finalyearproject.utilities.Assets;

//TODO - change the map load directory to sort out names

public class HistoryLevel extends GameScene {
    private boolean collisionLayer;
    /*
     * Fields
     */
    private TiledMap map = new TmxMapLoader().load("levels/geography/village.tmx");
    private Player player = new Player(40, 40, Assets.KNIGHT_SPRITE);
    private EducationGame game;

    public HistoryLevel(EducationGame game) {
        this.game = game;
        createGameLevel(map, player);
    }

//    public void checkCollision() {
//        collisionLayer = map.getLayers().;
//        if (map.getLayers().get("TerrainEdge"));
//    }
}
