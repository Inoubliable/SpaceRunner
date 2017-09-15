package com.timjanzelj.spacerunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    BitmapFont myFont;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private Viewport viewport;
    private Camera camera;

    public MenuScreen(SpaceRunner game) {
        this.game = game;
        bg = new Texture("sr_background.png");
        playBtn = new Texture("play_button.png");
        score = -1;

        camera = new PerspectiveCamera();
        viewport = new FitViewport(1920, 1080, camera);
    }

    public MenuScreen(SpaceRunner game, int finalScore) {
        this.game = game;
        bg = new Texture("sr_background.png");
        playBtn = new Texture("play_button.png");
        score = finalScore;
        scoreText = "" + score;

        //load and prepare free type font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("goodTimes.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 88;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";

        myFont = generator.generateFont(parameter);
        generator.dispose();

        camera = new PerspectiveCamera();
        viewport = new FitViewport(1920, 1080, camera);
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
        game.batch.draw(bg, 0, 0, SpaceRunner.WIDTH, SpaceRunner.HEIGHT);
        game.batch.draw(playBtn, SpaceRunner.WIDTH / 2 - playBtn.getWidth() / 2, SpaceRunner.HEIGHT / 2 - playBtn.getHeight() / 2);

        if(score > -1) {
            myFont.draw(game.batch, scoreText, SpaceRunner.WIDTH / 2, SpaceRunner.HEIGHT - 50);
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
