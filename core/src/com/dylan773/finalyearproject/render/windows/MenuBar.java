package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.utilities.Assets;

public class MenuBar extends Table {

    public MenuBar() {
        //Table menuTable = new Table(); // ASSETS.SKIN
        this.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.OPTIONS_BACKGROUND)));
        this.setSize(Gdx.graphics.getWidth(), 50f);

        TextButton button = new TextButton("hello", Assets.SKIN);
        this.add(button);
        //createMenuBar();
    }




    public Table createMenuBar() {
        Table menuTable = new Table(); // ASSETS.SKIN
        menuTable.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.OPTIONS_BACKGROUND)));
        menuTable.setSize(Gdx.graphics.getWidth(), 50f);

        TextButton button = new TextButton("hello", Assets.SKIN);
        menuTable.add(button);

        return menuTable;
    }
}
