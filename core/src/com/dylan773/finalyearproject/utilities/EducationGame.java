package com.dylan773.finalyearproject.utilities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.dylan773.finalyearproject.render.screens.MenuScreen;

// TODO - change this as a loading screen

/**
 *
 */
public class EducationGame extends Game {

	// Fields
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	@Override
	public void create () {
		setScreen(new MenuScreen(this));
		//setScreen(new GameScreen());
		//TODO - Create a loading scree that transitions into the main menu
		// TODO - Javadoc @ annotations
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
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen); // Sets the new screen to the screen provided
	}
}
