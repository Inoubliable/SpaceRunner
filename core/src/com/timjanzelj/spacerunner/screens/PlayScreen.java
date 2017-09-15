package com.timjanzelj.spacerunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    private BitmapFont myFont;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private ShapeRenderer shapeRenderer;
    private Rectangle runnerRectangle;

    private Viewport viewport;
    private Camera camera;

    public PlayScreen(SpaceRunner game) {
        this.game = game;
        bg = new Texture("sr_background.png");
        platforms = new ArrayList<Platform>();
        platforms.add(new Platform(150, 100));
        platforms.add(new Platform(410, 180));
        platforms.add(new Platform(680, 120));
        platforms.add(new Platform(920, 210));
        platforms.add(new Platform(1200, 130));
        platforms.add(new Platform(1440, 220));
        platforms.add(new Platform(1700, 150));
        platforms.add(new Platform(1930, 240));
        platforms.add(new Platform(2200, 160));
        platforms.add(new Platform(2510, 120));
        platforms.add(new Platform(2780, 190));
        platforms.add(new Platform(3020, 210));
        platforms.add(new Platform(3270, 100));
        platforms.add(new Platform(3570, 190));
        platforms.add(new Platform(3870, 180));
        platforms.add(new Platform(4180, 120));
        platforms.add(new Platform(4490, 120));
        platforms.add(new Platform(4770, 100));
        platforms.add(new Platform(5070, 190));
        cookies = new ArrayList<Cookie>();
        enemies = new ArrayList<Enemy>();

        Enemy modelEnemy = new Enemy(0, 0);
        Cookie modelCookie = new Cookie(0, 0);
        int x;
        int y;
        int enemyX;
        int enemyY;
        int cookieX;
        int cookieY;
        for (int i = 2; i <= platforms.size(); i += 3) {
            x = (int)platforms.get(i).getPosition().x;
            y = (int)platforms.get(i).getPosition().y;
            enemyX = x + (platforms.get(i).getW() - modelEnemy.getW()) / 2;
            enemyY = y + platforms.get(i).getH();
            enemies.add(new Enemy(enemyX, enemyY));
        }
        cookiesToRemove = new ArrayList<Cookie>();
        for (int i = 3; i <= platforms.size(); i += 3) {
            x = (int)platforms.get(i).getPosition().x;
            y = (int)platforms.get(i).getPosition().y;
            cookieX = x + (platforms.get(i).getW() - modelCookie.getW()) / 2;
            cookieY = y + platforms.get(i).getH() + modelCookie.getH();
            cookies.add(new Cookie(cookieX, cookieY));
        }
        runner = new Runner(game, 140, 250);

        score = 0;
        scoreText = "" + score;

        //load and prepare free type font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("goodTimes.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";

        myFont = generator.generateFont(parameter);
        generator.dispose();

        shapeRenderer = new ShapeRenderer();
        runnerRectangle = new Rectangle();

        camera = new PerspectiveCamera();
        viewport = new FitViewport(1920, 1080, camera);
    }

    @Override
    public void show() {

    }

    public void listenForInput() {
        if(Gdx.input.justTouched()) {
            runner.jump();
        }
        float accelY = Gdx.input.getAccelerometerY();
        if (accelY < -1){
            runner.left();
            for (Cookie cookie: cookies) {
                cookie.right(accelY);
            }
            for (Platform platform: platforms) {
                platform.right(accelY);
            }
            for (Enemy enemy: enemies) {
                enemy.right(accelY);
            }
        }
        if (accelY > 1){
            runner.right();
            for (Platform platform: platforms) {
                platform.left(accelY);
            }
            for (Cookie cookie: cookies) {
                cookie.left(accelY);
            }
            for (Enemy enemy: enemies) {
                enemy.left(accelY);
            }
        }
/*        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
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
        }*/
    }

    public void update(float delta) {
        listenForInput();

        runner.update(delta);
        for (Platform platform: platforms) {
            if (runner.getPosition().x + runner.getW() > platform.getPosition().x && runner.getPosition().x < (platform.getPosition().x + platform.getW())) {
                if ((platform.getPosition().y + platform.getH()) - runner.getPosition().y < 20 && (platform.getPosition().y + platform.getH()) - runner.getPosition().y > 0) {
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
            Rectangle enemyRectangle = new Rectangle(enemy.getPosition().x + (float)(enemy.getW() * 0.15), enemy.getPosition().y, (float)(enemy.getW() * 0.7), enemy.getH());
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
        game.batch.draw(bg, 0, 0, SpaceRunner.WIDTH, SpaceRunner.HEIGHT);
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
            //shapeRenderer.rect(enemy.getPosition().x + (float)(enemy.getW() * 0.17), enemy.getPosition().y, (float)(enemy.getW() * 0.66), enemy.getH());
        }
        game.batch.draw(runner.getTexture(), runner.getPosition().x, runner.getPosition().y);
        runnerRectangle.set(runner.getRectangle());
        //shapeRenderer.rect(runnerRectangle.x, runnerRectangle.y, runnerRectangle.width, runnerRectangle.height);

        myFont.draw(game.batch, scoreText, SpaceRunner.WIDTH - 60, SpaceRunner.HEIGHT - 60);
        //shapeRenderer.end();
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
    }

    public void win() {
        game.setScreen(new MenuScreen(game));
    }
}
