package com.dylan773.finalyearproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.dylan773.finalyearproject.render.screens.MenuScreen;
import com.dylan773.finalyearproject.utilities.Assets;

/**
 *
 */
public class EducationGame extends Game {

	// Fields
	public static EducationGame CLIENT;



	// TODO - imporve javadoc
	/**
	 * A cheap fix for a global variable.
	 * <p>
	 *
	 * This variable determines whether an info window is shown upon a {@link com.dylan773.finalyearproject.level.GameLevel} start.
	 */
	private boolean hideLevelWindows = false;

	public void setLevelInfoVisibility(Boolean value) { // TODO - static wont let me obtain in other classes???
		hideLevelWindows = value;
	}

	public boolean isLevelWindowsHidden() {
		return hideLevelWindows;
	}

	@Override
	public void create () {
		CLIENT = this;
		setScreen(new MenuScreen());
		//TODO - Create a loading scree that transitions into the main menu
//		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); // TODO - activate when finished
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

		Preferences showWindow = Gdx.app.getPreferences("EduQuizPreference");
		showWindow.putBoolean("dontShowWelcome", true);
		showWindow.flush();
		showWindow.getBoolean("dontShowWelcome");
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen); // Sets the new screen to the screen provided
	}
}
