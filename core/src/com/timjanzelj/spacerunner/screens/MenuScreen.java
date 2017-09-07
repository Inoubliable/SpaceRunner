package com.timjanzelj.spacerunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.timjanzelj.spacerunner.SpaceRunner;

/**
 * Created by TJ on 5.3.2017.
 */
public class MenuScreen implements Screen {
    private SpaceRunner game;
    private Texture bg;
    private Texture playBtn;
    private int score;
    private String scoreText;
    BitmapFont yourBitmapFontName;

    public MenuScreen(SpaceRunner game) {
        this.game = game;
        bg = new Texture("sr_background.png");
        playBtn = new Texture("play_button.png");
        score = -1;
    }

    public MenuScreen(SpaceRunner game, int finalScore) {
        this.game = game;
        bg = new Texture("sr_background.png");
        playBtn = new Texture("play_button.png");
        score = finalScore;
        scoreText = "" + score;
        yourBitmapFontName = new BitmapFont();
    }

    @Override
    public void show() {

    }

    public void listenForInput() {
        if(Gdx.input.justTouched()) {
            play();
        }
    }

    @Override
    public void render(float delta) {
        listenForInput();

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.batch.draw(playBtn, SpaceRunner.WIDTH / 2 - playBtn.getWidth() / 2, SpaceRunner.HEIGHT / 2 - playBtn.getHeight() / 2);

        if(score > -1) {
            yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            yourBitmapFontName.draw(game.batch, scoreText, SpaceRunner.WIDTH / 2, SpaceRunner.HEIGHT - 50);
            yourBitmapFontName.getData().setScale(3);
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        playBtn.dispose();
    }

    public void play() {
        game.setScreen(new PlayScreen(game));
        dispose();
    }
}
