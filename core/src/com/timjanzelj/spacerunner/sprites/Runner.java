package com.timjanzelj.spacerunner.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timjanzelj.spacerunner.SpaceRunner;
import com.timjanzelj.spacerunner.screens.MenuScreen;
import com.timjanzelj.spacerunner.screens.PlayScreen;

/**
 * Created by TJ on 5.3.2017.
 */
public class Runner extends Sprite {

    private static final int GRAVITY = -24;
    private static final int MAX_JUMPS = 4;

    private SpaceRunner game;
    private Vector2 position;
    private Vector2 velocity;
    private int jump_count;

    private Texture runner;
    private Texture runner_left;
    private Texture runner_right;
    private Texture runner_jump_left;
    private Texture runner_jump_right;

    private Rectangle runnerRectangle;
    private float bodyX;
    private float bodyY;
    private int bodyWidth;
    private int bodyHeight;

    public Runner(SpaceRunner game, int x, int y) {
        this.game = game;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        jump_count = 0;
        runner = new Texture("space_runner_right.png");
        runner_left = new Texture("space_runner_left.png");
        runner_right = new Texture("space_runner_right.png");
        runner_jump_left = new Texture("space_runner_jump_left.png");
        runner_jump_right = new Texture("space_runner_jump_right.png");

        bodyX = position.x;
        bodyY = position.y;
        bodyWidth = runner.getWidth();
        bodyHeight = runner.getHeight();
        runnerRectangle = new Rectangle();
        runnerRectangle.set(position.x, position.y, bodyWidth, bodyHeight);
    }

    public void update(float dt) {
        velocity.add(0, GRAVITY);
        velocity.scl(dt);
        position.add(velocity);
        if(position.y < 0) {
            gameOver(PlayScreen.score);
        }

        velocity.scl(1/dt);

        if(runner == runner_jump_left || runner == runner_jump_right) {
            bodyWidth = (int) Math.round(runner.getWidth() * 0.7);
            bodyHeight = (int) Math.round(runner.getHeight() * 0.7);
            bodyX = position.x + ((runner.getWidth() - bodyWidth) / 2);
            bodyY = position.y + (runner.getHeight() - bodyHeight);
            runnerRectangle.set(bodyX, bodyY, bodyWidth, bodyHeight);
        } else {
            bodyWidth = runner.getWidth();
            bodyHeight = runner.getHeight();
            bodyX = position.x;
            bodyY = position.y;
            runnerRectangle.set(bodyX, bodyY, bodyWidth, bodyHeight);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return runner;
    }

    public Rectangle getRectangle() {
        return runnerRectangle;
    }

    public int getW() {
        return runner.getWidth();
    }

    public int getH() {
        return runner.getHeight();
    }

    public void setPositionY(int y) {
        position.y = y;
    }

    public void setVelocityY(int y) {
        velocity.y = y;
    }

    public void setJumpCount(int count) {
        jump_count = count;
    }

    public void right() {
        runner = runner_right;

        if(velocity.y > 0) {
            runner = runner_jump_right;
        }
    }

    public void left() {
        runner = runner_left;

        if(velocity.y > 0) {
            runner = runner_jump_left;
        }
    }

    public void jump() {
        if(jump_count < MAX_JUMPS) {
            velocity.y = 510;
            jump_count++;
        }
    }

    public void gameOver(int score) {
        game.setScreen(new MenuScreen(game, score));
    }
}
