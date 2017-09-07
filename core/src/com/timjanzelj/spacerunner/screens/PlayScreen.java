package com.timjanzelj.spacerunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.timjanzelj.spacerunner.SpaceRunner;
import com.timjanzelj.spacerunner.sprites.Cookie;
import com.timjanzelj.spacerunner.sprites.Enemy;
import com.timjanzelj.spacerunner.sprites.Platform;
import com.timjanzelj.spacerunner.sprites.Runner;

import java.util.ArrayList;

/**
 * Created by TJ on 5.3.2017.
 */
public class PlayScreen implements Screen {
    private SpaceRunner game;
    private Texture bg;
    private ArrayList<Platform> platforms;
    private ArrayList<Cookie> cookies;
    private ArrayList<Cookie> cookiesToRemove;
    private ArrayList<Enemy> enemies;
    private Runner runner;

    public static int score;
    private String scoreText;
    BitmapFont yourBitmapFontName;

    private ShapeRenderer shapeRenderer;
    private Rectangle runnerRectangle;

    public PlayScreen(SpaceRunner game) {
        this.game = game;
        bg = new Texture("sr_background.png");
        platforms = new ArrayList<Platform>();
        platforms.add(new Platform(150, 100));
        platforms.add(new Platform(260, 130));
        platforms.add(new Platform(380, 120));
        platforms.add(new Platform(470, 160));
        platforms.add(new Platform(600, 150));
        platforms.add(new Platform(690, 180));
        platforms.add(new Platform(800, 170));
        platforms.add(new Platform(880, 200));
        platforms.add(new Platform(1000, 190));
        platforms.add(new Platform(1160, 140));
        platforms.add(new Platform(1280, 150));
        platforms.add(new Platform(1370, 180));
        platforms.add(new Platform(1470, 130));
        platforms.add(new Platform(1620, 160));
        platforms.add(new Platform(1770, 170));
        platforms.add(new Platform(1930, 150));
        platforms.add(new Platform(2090, 150));
        platforms.add(new Platform(2220, 140));
        platforms.add(new Platform(2370, 160));
        cookies = new ArrayList<Cookie>();
        enemies = new ArrayList<Enemy>();
        int x;
        int y;
        for (int i = 2; i <= platforms.size(); i += 3) {
            x = (int)platforms.get(i).getPosition().x;
            y = (int)platforms.get(i).getPosition().y;
            enemies.add(new Enemy(x + 5, y + 38));
        }
        cookiesToRemove = new ArrayList<Cookie>();
        for (int i = 3; i <= platforms.size(); i += 3) {
            x = (int)platforms.get(i).getPosition().x;
            y = (int)platforms.get(i).getPosition().y;
            cookies.add(new Cookie(x + 20, y + 50));
        }
        runner = new Runner(game, 140, 250);

        score = 0;
        scoreText = "" + score;
        yourBitmapFontName = new BitmapFont();

        shapeRenderer = new ShapeRenderer();
        runnerRectangle = new Rectangle();
    }

    @Override
    public void show() {

    }

    public void listenForInput() {
        if(Gdx.input.justTouched()) {
            runner.jump();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            runner.left();
            for (Cookie cookie: cookies) {
                cookie.right();
            }
            for (Platform platform: platforms) {
                platform.right();
            }
            for (Enemy enemy: enemies) {
                enemy.right();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            runner.right();
            for (Platform platform: platforms) {
                platform.left();
            }
            for (Cookie cookie: cookies) {
                cookie.left();
            }
            for (Enemy enemy: enemies) {
                enemy.left();
            }
        }
    }

    public void update(float delta) {
        listenForInput();

        runner.update(delta);
        for (Platform platform: platforms) {
            if (runner.getPosition().x + runner.getW() > platform.getPosition().x && runner.getPosition().x < (platform.getPosition().x + platform.getW())) {
                if ((platform.getPosition().y + platform.getH()) - runner.getPosition().y < 6 && (platform.getPosition().y + platform.getH()) - runner.getPosition().y > 0) {
                    runner.setVelocityY(0);
                    runner.setPositionY((int) (platform.getPosition().y + platform.getH()));
                    runner.setJumpCount(0);
                }
            }
        }
        for (Cookie cookie: cookies) {
            if (runner.getPosition().x + runner.getW() > cookie.getPosition().x && runner.getPosition().x < (cookie.getPosition().x + cookie.getW())) {
                if ((cookie.getPosition().y + cookie.getH()) > runner.getPosition().y && cookie.getPosition().y < runner.getPosition().y + runner.getH()) {
                    cookiesToRemove.add(cookie);

                    score++;
                    scoreText = "" + score;
                }
            }
        }
        for (Enemy enemy: enemies) {
            Rectangle enemyRectangle = new Rectangle(enemy.getPosition().x, enemy.getPosition().y, enemy.getW(), enemy.getH());
            if (Intersector.overlaps(runnerRectangle, enemyRectangle)) {
                runner.gameOver(score);
            }
        }

        cookies.removeAll(cookiesToRemove);
        if(cookies.isEmpty()) {
            win();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.RED);
        for (Platform platform:platforms) {
            game.batch.draw(platform.getTexture(), platform.getPosition().x, platform.getPosition().y);
        }
        for (Cookie cookie:cookies) {
            game.batch.draw(cookie.getTexture(), cookie.getPosition().x, cookie.getPosition().y);
        }
        for (Enemy enemy:enemies) {
            game.batch.draw(enemy.getTexture(), enemy.getPosition().x, enemy.getPosition().y);
            //shapeRenderer.rect(enemy.getPosition().x, enemy.getPosition().y, enemy.getW(), enemy.getH());
        }
        game.batch.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        runnerRectangle.set(runner.getRectangle());
        //shapeRenderer.rect(runnerRectangle.x, runnerRectangle.y, runnerRectangle.width, runnerRectangle.height);

        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        yourBitmapFontName.draw(game.batch, scoreText, SpaceRunner.WIDTH - 30, SpaceRunner.HEIGHT - 30);
        //shapeRenderer.end();
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
    }

    public void win() {
        game.setScreen(new MenuScreen(game));
    }
}
