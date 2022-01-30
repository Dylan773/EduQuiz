package com.dylan773.finalyearproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.dylan773.finalyearproject.render.screens.MenuScreen;
import com.dylan773.finalyearproject.utilities.Assets;

/**
 *
 */
public class EducationGame extends Game {

	// Fields
	/**
	 * Default height and width for this application.
	 */
	public final int WIDTH = 1280, HEIGHT = 720;
	public static final float PPM = 100; //pixels per meter
	public static EducationGame CLIENT;

	@Override
	public void create () {
		CLIENT = this;
		setScreen(new MenuScreen());
		//TODO - Create a loading scree that transitions into the main menu
	}

	@Override
	public void render () {
		super.render();
		//screen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/**
	 * Calls disposeAssets from the Assets class to dispose of all assets once this application has closed.
	 */
	@Override
	public void dispose () {
		Assets.disposeAssets();
		// level 1,2,3 dispose
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen); // Sets the new screen to the screen provided
	}
}
