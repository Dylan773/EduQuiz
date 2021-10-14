package com.dylan773.finalyearproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.dylan773.finalyearproject.screens.MainMenu;


public class StartScreen extends Game {
	
	@Override
	public void create () {
		setScreen(new MainMenu());
	}

	@Override
	public void render () {
		screen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {

	}

	@Override
	public void setScreen(Screen screen) {
		screen.dispose();
		super.setScreen(screen);
	}
}
