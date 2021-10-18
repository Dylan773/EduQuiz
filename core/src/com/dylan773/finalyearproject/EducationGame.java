package com.dylan773.finalyearproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.dylan773.finalyearproject.screens.MenuScreen;

// TODO - change this as a loading screen

/**
 *
 */
public class EducationGame extends Game {

	// Fields


	@Override
	public void create () {
		setScreen(new MenuScreen(this));

		//TODO - Create a loading scree that transitions into the main menu
	}

	@Override
	public void render () {
		screen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose () {

	}

	@Override
	public void setScreen(Screen screen) {
		screen.dispose(); // Disposes the current scene
		super.setScreen(screen); // Sets the new screen to the screen provided
	}
}
