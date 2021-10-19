package com.dylan773.finalyearproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dylan773.finalyearproject.Assets;
import com.dylan773.finalyearproject.EducationGame;

/**
 *
 */

//TODO - Change title font and colour
public class MenuScreen extends ScreenAdapter {

    // Fields
    private Stage stage;
    private Table table;
    private final EducationGame game;

    // Constructor
    /**
     *
     * @param game
     */
    public MenuScreen(EducationGame game) {
        this.game = game;

        table = new Table();
        table.setFillParent(true);

        stage = new Stage();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        // TODO - debug
        //table.setDebug(true);

        constructContent();
    }

    /**
     *
     */
    public void constructContent() {
        table.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.menuBackground)));

        // Title Label
        Label gameLabel = new Label("Edu Quiz", Assets.SKIN, "title");
        table.add(gameLabel).pad(0, 0, 20, 0).row();


        addButton("Play Game").addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                game.setScreen(new OptionsScreen(game));
//            }
        });

        addButton("Options").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new OptionsScreen(game));
            }
        });

        addButton("Exit Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
    }

    /**
     *
     * @param name
     * @return
     */
    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, Assets.SKIN);
        table.add(button).width(400f).padBottom(20f).row();

        return button;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen so it can draw from fresh
        stage.act(Gdx.graphics.getDeltaTime()); // act - tells the ui to perfrom actions (checks for inputs)
        stage.draw();

//        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // surprised this worked
    }

    @Override
    public void dispose() {
        //stage.dispose();
    }
}
