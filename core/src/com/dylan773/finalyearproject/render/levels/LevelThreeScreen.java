package com.dylan773.finalyearproject.render.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.entities.Player;
import com.dylan773.finalyearproject.render.screens.GameScene;
import com.dylan773.finalyearproject.utilities.Assets;

/**
 *
 */
public class LevelThreeScreen extends GameScene {

    private TiledMap levelThree = new TmxMapLoader().load
            //("levels/one.tmx");
    ("levels/history/history.tmx");
    private Player player = new Player(40, 40, Assets.KNIGHT_SPRITE);
    private EducationGame game;


    /**
     * <h2>Level One Constructor</h2>
     *
     * @param game
     */
    public LevelThreeScreen(EducationGame game) {
        this.game = game;
        //player = new Player()
        constructContent(levelThree, player);
        //constructContent();
    }


}
