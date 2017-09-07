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

    public Cookie(int x, int y) {
        position = new Vector2(x, y);
        cookie = new Texture("cookie.png");
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

    public void right() {
        position.x += 3;
    }

    public void left() {
        position.x -= 3;
    }

    public void dispose() {
        cookie.dispose();
    }
}
