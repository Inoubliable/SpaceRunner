package com.timjanzelj.spacerunner.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by TJ on 31.3.2017.
 */
public class Enemy {

    private Vector2 position;
    private Vector2 velocity;

    private Texture enemy;

    private Rectangle enemyRectangle;

    public Enemy(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        enemy = new Texture("enemy.png");

        enemyRectangle = new Rectangle();
        enemyRectangle.set(position.x, position.y, enemy.getWidth(), enemy.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return enemy;
    }

    public Rectangle getRectangle() {
        return enemyRectangle;
    }

    public int getW() {
        return enemy.getWidth();
    }

    public int getH() {
        return enemy.getHeight();
    }

    public void right() {
        position.x += 3;
    }

    public void left() {
        position.x -= 3;
    }
}
