package com.dylan773.finalyearproject.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dylan773.finalyearproject.EducationGame;

import java.awt.*;

//TODO - fix screen sizing at different resolutions + min/max screen size
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Edu Quiz";

		// Detects the users resolution
		Dimension a = Toolkit.getDefaultToolkit().getScreenSize();

		config.height = a.height;
		config.width = a.width;

		new LwjglApplication(new EducationGame(), config);
	}
}
