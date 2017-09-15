package com.timjanzelj.spacerunner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timjanzelj.spacerunner.screens.MenuScreen;

public class SpaceRunner extends Game {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final String TITLE = "Space Runner";

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
