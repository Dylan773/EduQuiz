package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.render.screens.MenuScreen;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Utilities.centreObject;


/**
 *
 */
public class GameBar extends Window {

    /**
     *
     */
    public GameBar() {
        super("", Assets.SKIN, "noBG");
        //super("", Assets.SKIN);
        buildWindow();
    }

    protected void buildWindow() {
        this.setVisible(false);
        this.setResizable(false);
        this.setMovable(false);
        this.align(0);
        this.setSize(Gdx.graphics.getWidth(), 140f);

        // Horizontal group for button placement
        HorizontalGroup group = new HorizontalGroup();
        group.space(150f); // Space between elements.
        group.center(); // Centre elements.

        TextButton btnHTP = new TextButton("Help", Assets.SKIN);
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
        textButton.setWidth(200f);
        this.add(textButton).padRight(100f);

        return textButton;
    }
}

//        //
//        addGameBarButton("Help").addListener(new ChangeListener() {
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
//                optionsWindow.setVisible(true);
//
//                //GameLevel.setOptionWindowVisibility(true);
//            }
//        });
//
//        //
//        addGameBarButton("Exit").addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                CLIENT.setScreen(new MenuScreen());
//            }
//        });
