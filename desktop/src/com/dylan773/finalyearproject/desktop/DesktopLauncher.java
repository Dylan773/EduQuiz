package com.dylan773.finalyearproject.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dylan773.finalyearproject.EducationGame;

//TODO - fix screen sizing at different resolutions
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Edu Quiz";
		config.height = EducationGame.HEIGHT;
		config.width = EducationGame.WIDTH;
//		config.height = 1080;
//		config.width = 1920;
		new LwjglApplication(new EducationGame(), config);
	}
}
