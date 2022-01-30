package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.render.screens.MenuScreen;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

/**
 *
 */
public class GameBar extends WindowBuilder {
    private EducationGame game = new EducationGame();

    /**
     *
     */
    public GameBar() {
        super(Gdx.graphics.getWidth(), 250);
        buildWindow();



    }

    @Override
    protected void buildWindow() {
//        this.setVisible(false);
//        this.setPosition(centreObject(getWidth(), Gdx.graphics.getWidth()), centreObject(getHeight(), Gdx.graphics.getHeight())); // TODO - figure this out
        HorizontalGroup group = new HorizontalGroup();

        //
//        addGameBarButton("How to Play").addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//
//            }
//        });
//
//        //
//        addGameBarButton("Options").addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//
//            }
//        });
//
//        //
//        addGameBarButton("Exit").addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new MenuScreen(game));
//            }
//        });

        TextButton btnHTP = new TextButton("How to Play", Assets.SKIN);
        TextButton btnOptions = new TextButton("Options", Assets.SKIN);
        TextButton btnExit = new TextButton("Exit", Assets.SKIN);

        group.addActor(btnHTP);
        group.addActor(btnOptions);
        group.addActor(btnExit);

        this.add(group);
    }

    /**
     *
     * @param name
     * @return
     */
    private TextButton addGameBarButton(String name) {
        TextButton textButton = new TextButton(name, Assets.SKIN);
        this.add(textButton).padRight(20f);

        return textButton;
    }
}
