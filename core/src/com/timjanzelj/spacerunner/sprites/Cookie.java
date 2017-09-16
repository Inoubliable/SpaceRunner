package com.timjanzelj.spacerunner.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by TJ on 6.3.2017.
 */
public class Cookie extends Sprite {

    private Vector2 position;

    private Texture cookie;

    public float alpha;

    public Cookie(int x, int y) {
        position = new Vector2(x, y);
        cookie = new Texture("cookie.png");
        alpha = 1;
    }

    public Texture getTexture() {
        return cookie;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getW() {
        return cookie.getWidth();
    }

    public int getH() {
        return cookie.getHeight();
    }

    public void right(float speed) {
        position.x -= speed * 2;
    }

    public void left(float speed) {
        position.x -= speed * 2;
    }

    public void pick() {
        position.y += 8;
        alpha -= 0.04;
    }

    public void dispose() {
        cookie.dispose();
    }
}
