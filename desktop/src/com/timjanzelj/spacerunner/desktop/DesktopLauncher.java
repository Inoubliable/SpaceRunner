package com.timjanzelj.spacerunner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.timjanzelj.spacerunner.SpaceRunner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SpaceRunner.WIDTH;
		config.height = SpaceRunner.HEIGHT;
		config.title = SpaceRunner.TITLE;
		new LwjglApplication(new SpaceRunner(), config);
	}
}
