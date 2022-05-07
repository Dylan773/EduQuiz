package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.dylan773.finalyearproject.utilities.Utilities;

import static com.dylan773.finalyearproject.utilities.Assets.SKIN;

public class AdminWindow extends Table {
    public AdminWindow() {
        add(Utilities.addLabel("feature coming soon", "subtitle", Color.CORAL));
    }
}
