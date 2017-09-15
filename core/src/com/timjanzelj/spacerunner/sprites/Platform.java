package com.timjanzelj.spacerunner.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by TJ on 5.3.2017.
 */
public class Platform extends Sprite {

    private Vector2 position;

    private Texture platform;

    public Platform(int x, int y) {
        position = new Vector2(x, y);
        platform = new Texture("platform1.png");
    }

    public Texture getTexture() {
        return platform;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getW() {
        return platform.getWidth();
    }

    public int getH() {
        return platform.getHeight();
    }

    public void right(float speed) {
        position.x -= speed * 2;
    }

    public void left(float speed) {
        position.x -= speed * 2;
    }
}
